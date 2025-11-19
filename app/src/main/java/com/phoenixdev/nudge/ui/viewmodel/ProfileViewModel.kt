package com.phoenixdev.nudge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenixdev.nudge.data.local.entity.User
import com.phoenixdev.nudge.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = userRepository.getCurrentUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun updateProfilePicture(uri: String) {
        viewModelScope.launch {
            val userId = userRepository.getCurrentUserId()
            userId?.let {
                userRepository.updateProfilePicture(it, uri)
            }
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            currentUser.value?.let { user ->
                val updatedUser = user.copy(name = name)
                userRepository.updateUser(updatedUser)
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            currentUser.value?.let { user ->
                val updatedUser = user.copy(isDarkTheme = !user.isDarkTheme)
                userRepository.updateUser(updatedUser)
            }
        }
    }

    fun toggleNotifications() {
        viewModelScope.launch {
            currentUser.value?.let { user ->
                val updatedUser = user.copy(notificationsEnabled = !user.notificationsEnabled)
                userRepository.updateUser(updatedUser)
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            val userId = userRepository.getCurrentUserId()
            userId?.let {
                userRepository.deleteAccount(it)
            }
        }
    }
}
