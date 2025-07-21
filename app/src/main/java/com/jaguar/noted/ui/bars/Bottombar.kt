package com.jaguar.noted.ui.bars

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jaguar.noted.objects.Note
import com.jaguar.noted.viewModels.NotesViewModel

@Composable
fun Option(icon: ImageVector, text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        { onClick() }, modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, text)
            Text(text)
        }
    }
}

@Composable
fun Navbar(notesViewModel: NotesViewModel) {
    Box {
        BottomAppBar {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,

                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Option(
                        Icons.Outlined.Home, "Home"
                    ) { Log.i("TODO", "Home Clicked!") }
                    Option(
                        Icons.Outlined.Settings, "Settings"
                    ) { Log.i("TODO", "Settings Clicked!") }
                }
            }
        }
        FloatingActionButton(
            {
                notesViewModel.addNote(
                    Note(
                        "New Note",
                        "This is a new note",
                        emptyList(),
                        null,
                        null,
                        false
                    )
                )
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(52.dp)
                .offset(y = (-26).dp)
        ) {
            Icon(
                Icons.Outlined.Add, "Add", modifier = Modifier.size(32.dp)
            )
        }
    }
}
