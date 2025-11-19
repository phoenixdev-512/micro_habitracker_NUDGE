package com.phoenixdev.nudge.domain.repository

import com.phoenixdev.nudge.data.local.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String): Result<Long>
    suspend fun register(email: String, password: String, name: String): Result<Long>
    suspend fun logout()
    suspend fun getCurrentUserId(): Long?
    fun getCurrentUser(): Flow<User?>
    suspend fun updateUser(user: User)
    suspend fun updateProfilePicture(userId: Long, uri: String)
    suspend fun deleteAccount(userId: Long)
}
