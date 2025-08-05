package com.jaguar.noted.ui.bars

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jaguar.noted.ui.components.NewNoteBottomSheet
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
    var showSheet: Boolean by remember { mutableStateOf(false) }

    if (showSheet) NewNoteBottomSheet({ showSheet = false }, { notesViewModel.addNote(note = it) })
    Box {
        BottomAppBar {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,

                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Option(
                        Icons.Outlined.Home,
                        "Home",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight()
                    ) { Log.i("TODO", "Home Clicked!") }
                    Option(
                        Icons.Outlined.Settings, "Settings", modifier = Modifier.fillMaxSize()
                    ) { Log.i("TODO", "Settings Clicked!") }
                }
            }
        }
        FloatingActionButton(
            {
                showSheet = !showSheet
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-30).dp),
            shape = FloatingActionButtonDefaults.largeShape
        ) {
            Icon(
                Icons.Outlined.Add, "Add", modifier = Modifier.size(36.dp)
            )
        }
    }
}
