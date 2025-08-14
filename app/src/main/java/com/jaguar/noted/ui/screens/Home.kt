package com.jaguar.noted.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.jaguar.noted.backend.DatabaseViewModel
import com.jaguar.noted.backend.entities.Event
import com.jaguar.noted.backend.entities.Note
import com.jaguar.noted.backend.entities.Task
import com.jaguar.noted.ui.components.NoteCard
import com.jaguar.noted.ui.components.TaskCard
import com.jaguar.noted.ui.components.ViewEventBottomSheet
import com.jaguar.noted.ui.theme.Typography

@Composable
fun TasksList(
    tasks: List<Task>,
    modifier: Modifier,
    onCheckClick: (task: Task) -> Unit,
    onTaskClick: (task: Task) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        if (tasks.isEmpty()) item { Text("No tasks found") }
        else items(tasks) { task ->
            TaskCard(
                task, onCheckClick = { onCheckClick(task) }) {
                onTaskClick(task)
            }

            HorizontalDivider()
        }
    }
}

@Composable
fun NotesGrid(
    columns: Int, notes: List<Note>, modifier: Modifier, onNoteClick: (note: Note) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        if (notes.isEmpty()) item { Text("No notes found") }
        else items(notes) { note ->
            NoteCard(note) {
                onNoteClick(note)
            }
        }
    }

}

@Composable
fun Home(databaseViewModel: DatabaseViewModel, modifier: Modifier) {
    val context = LocalContext.current
    val tasks: List<Task> by databaseViewModel.tasks.collectAsState(emptyList())
    val notes: List<Note> by databaseViewModel.notes.collectAsState(emptyList())

    var viewEventSheet by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf<Event?>(null) }

    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Tasks:", style = Typography.titleLarge, modifier = Modifier.padding(8.dp))
        TasksList(
            tasks, Modifier.weight(1f), { task ->
                databaseViewModel.updateEvent(task.copy(isCompleted = !task.isCompleted))
                Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT).show()
            }) { task ->
            selectedEvent = task
            viewEventSheet = true
        }

        HorizontalDivider()
        Text("Notes:", style = Typography.titleLarge, modifier = Modifier.padding(8.dp))

        NotesGrid(
            2, notes, Modifier.weight(1f)
        ) { note ->
            selectedEvent = note
            viewEventSheet = true
        }
    }

    if (viewEventSheet && selectedEvent != null) {
        ViewEventBottomSheet(
            event = selectedEvent!!,
            onDismiss = { viewEventSheet = false },
            onDelete = { databaseViewModel.deleteEvent(selectedEvent!!) },
            onSave = { databaseViewModel.updateEvent(it) })
    }
}