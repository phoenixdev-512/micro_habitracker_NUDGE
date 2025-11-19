package com.phoenixdev.nudge.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.phoenixdev.nudge.domain.model.Priority
import com.phoenixdev.nudge.domain.model.TaskColor

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val isPinned: Boolean = false,
    val priority: Priority = Priority.LOW,
    val color: TaskColor = TaskColor.DEFAULT,
    val category: String = "My Tasks",
    val dueDate: Long? = null,
    val reminderTime: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)
