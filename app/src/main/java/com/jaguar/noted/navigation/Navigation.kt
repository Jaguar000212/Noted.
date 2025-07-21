package com.jaguar.noted.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jaguar.noted.json.NotesJSONSerializer
import com.jaguar.noted.ui.Home
import com.jaguar.noted.ui.bars.Header
import com.jaguar.noted.ui.bars.Navbar
import com.jaguar.noted.ui.theme.NotedTheme
import com.jaguar.noted.viewModels.NotesViewModel

@Composable
fun Navigation() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val serializer = NotesJSONSerializer("notes.json", context = context)
    val notesViewModel = NotesViewModel(serializer)

    NotedTheme {
        Scaffold(
            topBar = { Header(drawerState = drawerState, scope = scope) },
            bottomBar = { Navbar(notesViewModel) }
        ) { innerPadding ->
            Home(notesViewModel = notesViewModel, modifier = Modifier.padding(innerPadding))
        }
    }
}