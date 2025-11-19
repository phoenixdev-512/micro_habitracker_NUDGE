package com.phoenixdev.nudge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.phoenixdev.nudge.data.local.dao.TaskDao
import com.phoenixdev.nudge.data.local.dao.UserDao
import com.phoenixdev.nudge.data.local.entity.Task
import com.phoenixdev.nudge.data.local.entity.User

@Database(
    entities = [User::class, Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NudgeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
}
