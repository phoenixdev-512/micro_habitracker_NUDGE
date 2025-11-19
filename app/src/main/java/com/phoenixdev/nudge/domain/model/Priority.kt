package com.phoenixdev.nudge.domain.model

import androidx.compose.ui.graphics.Color

enum class Priority(val displayName: String, val color: Color) {
    HIGH("High", Color(0xFFFF5252)),
    MEDIUM("Medium", Color(0xFFFFC107)),
    LOW("Low", Color(0xFF4CAF50))
}
