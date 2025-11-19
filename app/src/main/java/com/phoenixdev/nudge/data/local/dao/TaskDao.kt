package com.phoenixdev.nudge.data.local.dao

import androidx.room.*
import com.phoenixdev.nudge.data.local.entity.Task
import com.phoenixdev.nudge.domain.model.Priority
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE userId = :userId ORDER BY isPinned DESC, createdAt DESC")
    fun getAllTasks(userId: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE userId = :userId AND isCompleted = 0 ORDER BY priority ASC, dueDate ASC")
    fun getActiveTasks(userId: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE userId = :userId AND isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedTasks(userId: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE userId = :userId AND isPinned = 1 ORDER BY createdAt DESC")
    fun getPinnedTasks(userId: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE userId = :userId AND priority = :priority ORDER BY dueDate ASC")
    fun getTasksByPriority(userId: Long, priority: Priority): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE userId = :userId AND category = :category ORDER BY createdAt DESC")
    fun getTasksByCategory(userId: Long, category: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): Task?

    @Query("SELECT * FROM tasks WHERE userId = :userId AND (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%') ORDER BY createdAt DESC")
    fun searchTasks(userId: Long, query: String): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks WHERE userId = :userId AND isCompleted = 1")
    suspend fun deleteCompletedTasks(userId: Long)

    @Query("SELECT COUNT(*) FROM tasks WHERE userId = :userId AND isCompleted = 1")
    fun getCompletedTaskCount(userId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE userId = :userId AND isCompleted = 0")
    fun getActiveTaskCount(userId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE userId = :userId AND priority = :priority AND isCompleted = 0")
    fun getTaskCountByPriority(userId: Long, priority: Priority): Flow<Int>
}
