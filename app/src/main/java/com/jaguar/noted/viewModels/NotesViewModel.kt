package com.jaguar.noted.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaguar.noted.json.NotesJSONSerializer
import com.jaguar.noted.objects.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val serializer: NotesJSONSerializer) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _notes.value = serializer.loadNotes()
        }
    }

    private fun saveNotes(notes: List<Note>) {
        viewModelScope.launch {
            serializer.saveNotes(notes)
            _notes.value = notes
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            val notes = _notes.value.toMutableList()
            val index = notes.indexOfFirst { it.getID() == note.getID() }
            if (index != -1) {
                notes[index] = note
                saveNotes(notes)
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            val notes = _notes.value.toMutableList()
            notes.add(0, note)
            saveNotes(notes)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            val notes = _notes.value.toMutableList()
            notes.remove(note)
            saveNotes(notes)
        }
    }


}