package com.phoenixdev.nudge.domain.model

data class TaskStatistics(
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val activeTasks: Int = 0,
    val highPriorityTasks: Int = 0
)
