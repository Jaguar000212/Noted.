package com.jaguar.noted.backend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jaguar.noted.backend.daos.TaskDao
import com.jaguar.noted.backend.entities.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DatabaseViewModel(private val taskDao: TaskDao) : ViewModel() {
    val tasks: Flow<List<Task>> = taskDao.getAll()

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }
}

class DatabaseViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatabaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DatabaseViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}