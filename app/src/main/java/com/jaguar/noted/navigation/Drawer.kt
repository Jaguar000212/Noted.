package com.jaguar.noted.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaguar.noted.R
import com.jaguar.noted.backend.DatabaseViewModel
import com.jaguar.noted.ui.components.AddListDialog
import com.jaguar.noted.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun NavigationIcon(icon: ImageVector, label: String) {
    Icon(
        icon, contentDescription = label, modifier = Modifier.padding(horizontal = 18.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(
    drawerState: DrawerState, databaseViewModel: DatabaseViewModel, content: @Composable () -> Unit
) {
    val eventLists = databaseViewModel.eventLists.collectAsState(emptyList())
    LocalContext.current
    val scope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        BasicAlertDialog(onDismissRequest = { showDialog = false }) {
            AddListDialog(databaseViewModel, { showDialog = false })
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            ModalDrawerSheet(content = {
                Text(
                    stringResource(R.string.app_name),
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("All") },
                    selected = false,
                    icon = { Icon(Icons.Outlined.Search, "All Events") },
                    onClick = {
                        databaseViewModel.getEvents()
                        scope.launch { drawerState.close() }
                    })

                NavigationDrawerItem(
                    label = { Text("Today") },
                    selected = false,
                    icon = { Icon(Icons.Outlined.Place, "Today") },
                    onClick = { })

                NavigationDrawerItem(
                    label = { Text("Due in 7 Days") },
                    selected = false,
                    icon = { Icon(Icons.Outlined.DateRange, "Due in 7 days") },
                    onClick = { })

                HorizontalDivider()

                eventLists.value.forEach { eventList ->
                    NavigationDrawerItem(
                        label = { Text(eventList.name) },
                        selected = false,
                        icon = { Text(eventList.emoji) },
                        onClick = {
                            databaseViewModel.getEvents(eventList)
                            scope.launch { drawerState.close() }
                        })
                }

                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Add List") },
                    selected = false,
                    icon = { Icon(Icons.Outlined.Add, "Add List") },
                    onClick = {
                        showDialog = true
                    })

                HorizontalDivider()
            })
        }) {
        content()
    }
}
