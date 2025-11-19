package com.phoenixdev.nudge.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import at.favre.lib.crypto.bcrypt.BCrypt
import com.phoenixdev.nudge.data.local.dao.UserDao
import com.phoenixdev.nudge.data.local.entity.User
import com.phoenixdev.nudge.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val dataStore: DataStore<Preferences>
) : UserRepository {

    companion object {
        private val USER_ID_KEY = longPreferencesKey("user_id")
    }

    override suspend fun login(email: String, password: String): Result<Long> {
        return try {
            val user = userDao.getUserByEmail(email)
                ?: return Result.failure(Exception("User not found"))

            val passwordMatches = BCrypt.verifyer()
                .verify(password.toCharArray(), user.passwordHash)
                .verified

            if (!passwordMatches) {
                return Result.failure(Exception("Invalid password"))
            }

            // Update last login time
            userDao.updateLastLogin(user.id, System.currentTimeMillis())

            // Save user ID to DataStore
            dataStore.edit { preferences ->
                preferences[USER_ID_KEY] = user.id
            }

            Result.success(user.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String, name: String): Result<Long> {
        return try {
            // Check if user already exists
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                return Result.failure(Exception("Email already registered"))
            }

            // Hash password
            val passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())

            // Create new user
            val user = User(
                email = email,
                passwordHash = passwordHash,
                name = name
            )

            val userId = userDao.insertUser(user)

            // Save user ID to DataStore
            dataStore.edit { preferences ->
                preferences[USER_ID_KEY] = userId
            }

            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
        }
    }

    override suspend fun getCurrentUserId(): Long? {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }.firstOrNull()
    }

    override fun getCurrentUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }.map { userId ->
            userId?.let { userDao.getUserById(it).firstOrNull() }
        }
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun updateProfilePicture(userId: Long, uri: String) {
        userDao.updateProfilePicture(userId, uri)
    }

    override suspend fun deleteAccount(userId: Long) {
        val user = userDao.getUserById(userId).firstOrNull()
        user?.let {
            userDao.deleteUser(it)
            logout()
        }
    }
}
