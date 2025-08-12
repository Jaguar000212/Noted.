package com.jaguar.noted.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jaguar.noted.backend.NotedDatabase
import com.jaguar.noted.backend.DatabaseViewModel
import com.jaguar.noted.backend.DatabaseViewModelFactory
import com.jaguar.noted.ui.bars.Header
import com.jaguar.noted.ui.bars.Navbar
import com.jaguar.noted.ui.screens.Home
import com.jaguar.noted.ui.theme.NotedTheme

@Composable
fun Navigation() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val databaseViewModel: DatabaseViewModel = remember {
        DatabaseViewModelFactory(
            NotedDatabase.getDatabase(context).taskDao()
        ).create(DatabaseViewModel::class.java)
    }

    NotedTheme {
        Scaffold(
            topBar = { Header(drawerState = drawerState, scope = scope) },
            bottomBar = { Navbar(databaseViewModel) }) { innerPadding ->
            Home(databaseViewModel = databaseViewModel, modifier = Modifier.padding(innerPadding))
        }
    }
}