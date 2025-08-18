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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DatabaseViewModel(
    private val taskDao: TaskDao,
    private val noteDao: NoteDao,
    private val eventListDao: EventListDao
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    val eventLists: Flow<List<EventList>> = eventListDao.getAll()

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> get() = _isReady

    fun initConfigs() {
        viewModelScope.launch {
            getEvents()
            _isReady.value = true
            launch {
                eventLists.collectLatest { lists ->
                    if (lists.isEmpty()) {
                        eventListDao.insert(EventList(name = "Inbox", emoji = "📥"))
                    }
                }
            }
        }
    }

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

    fun getEvents(eventList: EventList? = null) {
        viewModelScope.launch {
            if (eventList != null) {
                combine(
                    noteDao.getNotesByList(eventList.id), taskDao.getTasksByList(eventList.id)
                ) { notes, tasks ->
                    _notes.value = notes
                    _tasks.value = tasks
                }.collect()
            } else {
                combine(
                    noteDao.getAll(), taskDao.getAll()
                ) { notes, tasks ->
                    _notes.value = notes
                    _tasks.value = tasks
                }.collect()
            }
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
            @Suppress("UNCHECKED_CAST") return DatabaseViewModel(
                taskDao, noteDao, eventListDao
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}