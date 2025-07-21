package com.jaguar.noted.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jaguar.noted.ui.components.NoteCard
import com.jaguar.noted.viewModels.NotesViewModel

@Composable
fun Home(notesViewModel: NotesViewModel, modifier: Modifier) {
    val context = LocalContext.current
    val notes by notesViewModel.notes.collectAsState()

    Box(modifier = modifier) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            if (notes.isEmpty()) item { Text("No notes found") }
            else items(notes) {
                NoteCard(it, {
                    notesViewModel.updateNote(it.copy(isCompleted = !it.getIsCompleted()))
                    Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
                }, {
                    notesViewModel.updateNote(it.copy(isCompleted = !it.getIsCompleted()))
                    Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
                })
                HorizontalDivider()
            }
        }
    }
}