package com.phoenixdev.nudge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenixdev.nudge.data.local.entity.Task
import com.phoenixdev.nudge.domain.model.Priority
import com.phoenixdev.nudge.domain.model.TaskColor
import com.phoenixdev.nudge.domain.model.TaskStatistics
import com.phoenixdev.nudge.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class TaskFilter {
    ALL, ACTIVE, COMPLETED, PINNED
}

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _currentFilter = MutableStateFlow(TaskFilter.ALL)
    val currentFilter: StateFlow<TaskFilter> = _currentFilter.asStateFlow()

    val statistics: StateFlow<TaskStatistics> = taskRepository.getStatistics()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TaskStatistics()
        )

    val tasks: StateFlow<List<Task>> = combine(
        _currentFilter,
        _searchQuery
    ) { filter, query ->
        Pair(filter, query)
    }.flatMapLatest { (filter, query) ->
        when {
            query.isNotBlank() -> taskRepository.searchTasks(query)
            else -> when (filter) {
                TaskFilter.ALL -> taskRepository.getAllTasks()
                TaskFilter.ACTIVE -> taskRepository.getActiveTasks()
                TaskFilter.COMPLETED -> taskRepository.getCompletedTasks()
                TaskFilter.PINNED -> taskRepository.getPinnedTasks()
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addTask(
        title: String,
        description: String = "",
        priority: Priority = Priority.LOW,
        color: TaskColor = TaskColor.DEFAULT,
        category: String = "My Tasks",
        dueDate: Long? = null,
        reminderTime: Long? = null
    ) {
        if (title.isBlank()) return

        viewModelScope.launch {
            val task = Task(
                userId = 0, // Will be set by repository
                title = title,
                description = description,
                priority = priority,
                color = color,
                category = category,
                dueDate = dueDate,
                reminderTime = reminderTime
            )
            taskRepository.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(
                isCompleted = !task.isCompleted,
                completedAt = if (!task.isCompleted) System.currentTimeMillis() else null
            )
            taskRepository.updateTask(updatedTask)
        }
    }

    fun toggleTaskPin(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isPinned = !task.isPinned)
            taskRepository.updateTask(updatedTask)
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilter(filter: TaskFilter) {
        _currentFilter.value = filter
    }

    fun clearCompletedTasks() {
        viewModelScope.launch {
            taskRepository.deleteCompletedTasks()
        }
    }
}
