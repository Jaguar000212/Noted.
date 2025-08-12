package com.jaguar.noted.ui.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jaguar.noted.backend.entities.Task
import com.jaguar.noted.backend.DatabaseViewModel
import com.jaguar.noted.ui.components.EventCard
import com.jaguar.noted.ui.components.ViewEventBottomSheet

@Composable
fun Home(databaseViewModel: DatabaseViewModel, modifier: Modifier) {
    val context = LocalContext.current
    val tasks: List<Task> by databaseViewModel.tasks.collectAsState(emptyList())

    var viewNoteSheet by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Box(modifier = modifier) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            if (tasks.isEmpty()) item { Text("No events found") }
            else items(tasks) { note ->
                EventCard(note, {
                    databaseViewModel.updateTask(note.copy(isCompleted = !note.isCompleted))
                    Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
                }, {
                    selectedTask = note
                    viewNoteSheet = true
                })
                HorizontalDivider()
            }
        }

        if (viewNoteSheet && selectedTask != null) {
            ViewEventBottomSheet(
                task = selectedTask!!,
                onDismiss = { viewNoteSheet = false },
                onDelete = { databaseViewModel.deleteTask(selectedTask!!) },
                onSave = { databaseViewModel.updateTask(it) })
        }
    }
}