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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jaguar.noted.backend.DatabaseViewModel
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
fun Navbar(databaseViewModel: DatabaseViewModel) {
    var showSheet: Boolean by remember { mutableStateOf(false) }
    var isTask: Boolean by remember { mutableStateOf(false) }

    if (showSheet) NewEventBottomSheet(
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
                    ) { Log.i("TODO", "Home Clicked!") }
                    Option(
                        Icons.Outlined.Settings, "Settings", modifier = Modifier.fillMaxSize()
                    ) { Log.i("TODO", "Settings Clicked!") }
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
