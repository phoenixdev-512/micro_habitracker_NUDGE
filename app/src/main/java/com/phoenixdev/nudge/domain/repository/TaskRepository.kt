package com.phoenixdev.nudge.domain.repository

import com.phoenixdev.nudge.data.local.entity.Task
import com.phoenixdev.nudge.domain.model.TaskStatistics
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getActiveTasks(): Flow<List<Task>>
    fun getCompletedTasks(): Flow<List<Task>>
    fun getPinnedTasks(): Flow<List<Task>>
    fun getTasksByCategory(category: String): Flow<List<Task>>
    fun searchTasks(query: String): Flow<List<Task>>
    suspend fun getTaskById(taskId: Long): Task?
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun deleteCompletedTasks()
    fun getStatistics(): Flow<TaskStatistics>
}
