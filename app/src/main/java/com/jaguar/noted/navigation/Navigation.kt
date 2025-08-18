package com.jaguar.noted.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jaguar.noted.backend.DatabaseViewModel
import com.jaguar.noted.backend.DatabaseViewModelFactory
import com.jaguar.noted.backend.NotedDatabase
import com.jaguar.noted.ui.bars.Header
import com.jaguar.noted.ui.bars.Navbar
import com.jaguar.noted.ui.screens.Home
import com.jaguar.noted.ui.screens.Settings
import com.jaguar.noted.ui.theme.NotedTheme

@Composable
fun Navigation() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val navController = rememberNavController()

    val databaseViewModel: DatabaseViewModel = remember {
        DatabaseViewModelFactory(
            NotedDatabase.getDatabase(context).taskDao(),
            NotedDatabase.getDatabase(context).noteDao(),
            NotedDatabase.getDatabase(context).eventListDao()
        ).create(DatabaseViewModel::class.java)
    }
    LaunchedEffect(Unit) {
        databaseViewModel.initConfigs()
    }
    val isReady by databaseViewModel.isReady.collectAsState()

    NotedTheme {
        Drawer(drawerState, databaseViewModel) {
            Scaffold(
                topBar = { Header(drawerState = drawerState, scope = scope) },
                bottomBar = { Navbar(databaseViewModel, navController) }) { innerPadding ->
                if (!isReady) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                    return@Scaffold
                }
                NavHost(navController = navController, startDestination = Home.route) {
                    composable(Home.route) {
                        Home(databaseViewModel, Modifier.padding(innerPadding))
                    }
                    composable(Settings.route) {
                        Settings(databaseViewModel, Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}