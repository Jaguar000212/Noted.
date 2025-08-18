package com.jaguar.noted.ui.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jaguar.noted.backend.DatabaseViewModel
import com.jaguar.noted.navigation.Home
import com.jaguar.noted.navigation.Settings
import com.jaguar.noted.ui.components.NewEventBottomSheet

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
fun Navbar(databaseViewModel: DatabaseViewModel, navController: NavController) {
    var showSheet: Boolean by remember { mutableStateOf(false) }
    var isTask: Boolean by remember { mutableStateOf(false) }
    val eventLists by databaseViewModel.eventLists.collectAsState(initial = emptyList())

    if (showSheet) NewEventBottomSheet(
        eventLists,
        { showSheet = false },
        { databaseViewModel.addEvent(it) },
        isTask = isTask
    )
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
                    ) { navController.navigate(Home.route) }
                    Option(
                        Icons.Outlined.Settings, "Settings", modifier = Modifier.fillMaxSize()
                    ) { navController.navigate(Settings.route) }
                }
            }
        }
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-30).dp)
        ) {
            SegmentedButton(
                false, {
                    isTask = true
                    showSheet = !showSheet
                }, shape = AbsoluteRoundedCornerShape(
                    topLeftPercent = 25,
                    topRightPercent = 0,
                    bottomLeftPercent = 25,
                    bottomRightPercent = 0
                ), icon = {
                    Icon(
                        Icons.Outlined.Add, "Add", modifier = Modifier.size(36.dp)
                    )
                }) {
                Text("Task")
            }
            SegmentedButton(
                false, {
                    isTask = false
                    showSheet = !showSheet
                }, shape = AbsoluteRoundedCornerShape(
                    topLeftPercent = 0,
                    topRightPercent = 25,
                    bottomLeftPercent = 0,
                    bottomRightPercent = 25
                ), icon = {
                    Icon(
                        Icons.Outlined.Add, "Add", modifier = Modifier.size(36.dp)
                    )
                }) {
                Text("Note")
            }
        }
    }
}

