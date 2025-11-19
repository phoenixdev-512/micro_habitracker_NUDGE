package com.phoenixdev.nudge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenixdev.nudge.domain.model.AuthState
import com.phoenixdev.nudge.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val userId = userRepository.getCurrentUserId()
            _authState.value = AuthState(
                isAuthenticated = userId != null,
                currentUserId = userId
            )
        }
    }

    fun login(email: String, password: String) {
        // Validation
        if (email.isBlank()) {
            _authState.value = _authState.value.copy(error = "Email cannot be empty")
            return
        }

        if (!isValidEmail(email)) {
            _authState.value = _authState.value.copy(error = "Please enter a valid email")
            return
        }

        if (password.isBlank()) {
            _authState.value = _authState.value.copy(error = "Password cannot be empty")
            return
        }

        if (password.length < 6) {
            _authState.value = _authState.value.copy(error = "Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            val result = userRepository.login(email, password)
            
            result.fold(
                onSuccess = { userId ->
                    _authState.value = AuthState(
                        isLoading = false,
                        isAuthenticated = true,
                        currentUserId = userId,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _authState.value = AuthState(
                        isLoading = false,
                        isAuthenticated = false,
                        currentUserId = null,
                        error = exception.message ?: "Login failed"
                    )
                }
            )
        }
    }

    fun register(email: String, password: String, confirmPassword: String, name: String) {
        // Validation
        if (name.isBlank()) {
            _authState.value = _authState.value.copy(error = "Name cannot be empty")
            return
        }

        if (email.isBlank()) {
            _authState.value = _authState.value.copy(error = "Email cannot be empty")
            return
        }

        if (!isValidEmail(email)) {
            _authState.value = _authState.value.copy(error = "Please enter a valid email")
            return
        }

        if (password.isBlank()) {
            _authState.value = _authState.value.copy(error = "Password cannot be empty")
            return
        }

        if (password.length < 6) {
            _authState.value = _authState.value.copy(error = "Password must be at least 6 characters")
            return
        }

        if (password != confirmPassword) {
            _authState.value = _authState.value.copy(error = "Passwords do not match")
            return
        }

        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            
            val result = userRepository.register(email, password, name)
            
            result.fold(
                onSuccess = { userId ->
                    _authState.value = AuthState(
                        isLoading = false,
                        isAuthenticated = true,
                        currentUserId = userId,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _authState.value = AuthState(
                        isLoading = false,
                        isAuthenticated = false,
                        currentUserId = null,
                        error = exception.message ?: "Registration failed"
                    )
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _authState.value = AuthState(
                isLoading = false,
                isAuthenticated = false,
                currentUserId = null,
                error = null
            )
        }
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
