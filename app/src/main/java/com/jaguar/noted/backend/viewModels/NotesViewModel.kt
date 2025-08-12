package com.jaguar.noted.backend.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaguar.noted.backend.daos.NoteDao
import com.jaguar.noted.backend.entities.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val notesDao: NoteDao) : ViewModel() {
    val notes: Flow<List<Note>> = notesDao.getAll()

    fun addNote(note: Note) {
        viewModelScope.launch {
            notesDao.insert(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            notesDao.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesDao.delete(note)
        }
    }
}