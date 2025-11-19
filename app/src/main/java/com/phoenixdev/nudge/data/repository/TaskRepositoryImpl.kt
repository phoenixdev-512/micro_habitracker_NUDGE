package com.phoenixdev.nudge.data.repository

import com.phoenixdev.nudge.data.local.dao.TaskDao
import com.phoenixdev.nudge.data.local.entity.Task
import com.phoenixdev.nudge.domain.model.Priority
import com.phoenixdev.nudge.domain.model.TaskStatistics
import com.phoenixdev.nudge.domain.repository.TaskRepository
import com.phoenixdev.nudge.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val userRepository: UserRepository
) : TaskRepository {

    private suspend fun requireUserId(): Long {
        return userRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in")
    }

    override fun getAllTasks(): Flow<List<Task>> = flow {
        val userId = requireUserId()
        taskDao.getAllTasks(userId).collect { emit(it) }
    }

    override fun getActiveTasks(): Flow<List<Task>> = flow {
        val userId = requireUserId()
        taskDao.getActiveTasks(userId).collect { emit(it) }
    }

    override fun getCompletedTasks(): Flow<List<Task>> = flow {
        val userId = requireUserId()
        taskDao.getCompletedTasks(userId).collect { emit(it) }
    }

    override fun getPinnedTasks(): Flow<List<Task>> = flow {
        val userId = requireUserId()
        taskDao.getPinnedTasks(userId).collect { emit(it) }
    }

    override fun getTasksByCategory(category: String): Flow<List<Task>> = flow {
        val userId = requireUserId()
        taskDao.getTasksByCategory(userId, category).collect { emit(it) }
    }

    override fun searchTasks(query: String): Flow<List<Task>> = flow {
        val userId = requireUserId()
        taskDao.searchTasks(userId, query).collect { emit(it) }
    }

    override suspend fun getTaskById(taskId: Long): Task? {
        return taskDao.getTaskById(taskId)
    }

    override suspend fun insertTask(task: Task): Long {
        val userId = requireUserId()
        return taskDao.insertTask(task.copy(userId = userId))
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.copy(updatedAt = System.currentTimeMillis()))
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun deleteCompletedTasks() {
        val userId = requireUserId()
        taskDao.deleteCompletedTasks(userId)
    }

    override fun getStatistics(): Flow<TaskStatistics> = flow {
        val userId = requireUserId()
        combine(
            taskDao.getAllTasks(userId),
            taskDao.getCompletedTaskCount(userId),
            taskDao.getActiveTaskCount(userId),
            taskDao.getTaskCountByPriority(userId, Priority.HIGH)
        ) { allTasks, completed, active, highPriority ->
            TaskStatistics(
                totalTasks = allTasks.size,
                completedTasks = completed,
                activeTasks = active,
                highPriorityTasks = highPriority
            )
        }.collect { emit(it) }
    }
}
