package com.jaguar.noted.backend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jaguar.noted.backend.daos.EventListDao
import com.jaguar.noted.backend.daos.NoteDao
import com.jaguar.noted.backend.daos.TaskDao
import com.jaguar.noted.backend.entities.Event
import com.jaguar.noted.backend.entities.EventList
import com.jaguar.noted.backend.entities.Note
import com.jaguar.noted.backend.entities.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DatabaseViewModel(
    private val taskDao: TaskDao,
    private val noteDao: NoteDao,
    private val eventListDao: EventListDao
) : ViewModel() {
    val tasks: Flow<List<Task>> = taskDao.getAll()
    val notes: Flow<List<Note>> = noteDao.getAll()
    val eventLists: Flow<List<EventList>> = eventListDao.getAll()

    fun addEvent(event: Event) {
        viewModelScope.launch {
            if (event is Task) taskDao.insert(event)
            else if (event is Note) noteDao.insert(event)
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            if (event is Task) taskDao.update(event)
            else if (event is Note) noteDao.update(event)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            if (event is Task) taskDao.delete(event)
            else if (event is Note) noteDao.delete(event)
        }
    }

    fun addEventList(eventList: EventList) {
        viewModelScope.launch {
            eventListDao.insert(eventList)
        }
    }

    fun updateEventList(eventList: EventList) {
        viewModelScope.launch {
            eventListDao.update(eventList)
        }
    }

    fun deleteEventList(eventList: EventList) {
        viewModelScope.launch {
            eventListDao.delete(eventList)
        }
    }
}

class DatabaseViewModelFactory(
    private val taskDao: TaskDao,
    private val noteDao: NoteDao,
    private val eventListDao: EventListDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatabaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DatabaseViewModel(taskDao, noteDao, eventListDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}