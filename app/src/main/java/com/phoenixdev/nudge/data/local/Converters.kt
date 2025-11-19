package com.phoenixdev.nudge.data.local

import androidx.room.TypeConverter
import com.phoenixdev.nudge.domain.model.Priority
import com.phoenixdev.nudge.domain.model.TaskColor

class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(value: String): Priority {
        return Priority.valueOf(value)
    }

    @TypeConverter
    fun fromTaskColor(taskColor: TaskColor): String {
        return taskColor.name
    }

    @TypeConverter
    fun toTaskColor(value: String): TaskColor {
        return TaskColor.valueOf(value)
    }
}
