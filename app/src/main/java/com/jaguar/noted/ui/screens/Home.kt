package com.jaguar.noted.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jaguar.noted.objects.Note
import com.jaguar.noted.ui.components.NoteCard
import com.jaguar.noted.ui.components.ViewNoteBottomSheet
import com.jaguar.noted.viewModels.NotesViewModel

@Composable
fun Home(notesViewModel: NotesViewModel, modifier: Modifier) {
    val context = LocalContext.current
    val notes by notesViewModel.notes.collectAsState()

    var viewNoteSheet by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }

    Box(modifier = modifier) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            if (notes.isEmpty()) item { Text("No notes found") }
            else items(notes) { note ->
                NoteCard(note, {
                    notesViewModel.updateNote(note.copy(isCompleted = !note.getIsCompleted()))
                    Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
                }, {
                    selectedNote = note
                    viewNoteSheet = true
                })
                if (note.getSubTasks().isNotEmpty()) {
                    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                        note.getSubTasks().forEach { subTask ->
                            NoteCard(subTask, {
                                notesViewModel.updateNote(subTask.copy(isCompleted = !subTask.getIsCompleted()))
                                Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
                            }, {
                                notesViewModel.updateNote(subTask.copy(isCompleted = !subTask.getIsCompleted()))
                                Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
                            })
                            HorizontalDivider()
                        }
                    }
                }
                HorizontalDivider()
            }
        }

        if (viewNoteSheet && selectedNote != null) {
            ViewNoteBottomSheet(
                note = selectedNote!!,
                onDismiss = { viewNoteSheet = false },
                onDelete = { notesViewModel.deleteNote(selectedNote!!) },
                onSave = { notesViewModel.updateNote(it) }
            )
        }
    }
}