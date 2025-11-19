package com.phoenixdev.nudge.domain.model

data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val currentUserId: Long? = null,
    val error: String? = null
)
