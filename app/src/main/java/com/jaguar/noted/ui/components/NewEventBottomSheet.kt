package com.jaguar.noted.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jaguar.noted.backend.entities.Event
import com.jaguar.noted.backend.entities.Note
import com.jaguar.noted.backend.entities.Task
import com.jaguar.noted.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEventBottomSheet(
    onDismiss: () -> Unit, onSave: (Event) -> Unit, isTask: Boolean, modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isDue: Boolean by remember { mutableStateOf(false) }

    var eventTitle by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventTags by remember { mutableStateOf(emptyList<String>()) }

    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(is24Hour = false)
    var showTimePicker by remember { mutableStateOf(false) }

    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var selectedHour by remember { mutableIntStateOf(9) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var selectedAfternoon by remember { mutableStateOf(false) }

    if (isTask) {
        if (showDatePicker) DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        selectedDateMillis = datePickerState.selectedDateMillis
                        isDue = true
                    }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }) {
            DatePicker(state = datePickerState)
        }

        if (showTimePicker) DatePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                        selectedHour =
                            if (timePickerState.hour == 0) 12 else if (timePickerState.hour > 12) timePickerState.hour - 12 else timePickerState.hour
                        selectedMinute = timePickerState.minute
                        selectedAfternoon = timePickerState.isAfternoon
                        isDue = true
                    }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showTimePicker = false }) {
                    Text("Cancel")
                }
            }) {
            TimePicker(
                state = timePickerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() }, modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("New Event", style = Typography.titleLarge)

            HorizontalDivider(Modifier.padding(vertical = 8.dp))

            OutlinedTextField(
                eventTitle,
                { eventTitle = it },
                singleLine = true,
                placeholder = { Text("What do you have in your mind...?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                eventDescription,
                { eventDescription = it },
                placeholder = { Text("Share some thoughts...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                minLines = 5,
                maxLines = 20
            )

            if (isTask) Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25))
                        .clickable { showTimePicker = true }) {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            String.format("%02d", selectedHour),
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            String.format("%02d", selectedMinute),
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Text(
                            if (selectedAfternoon) "PM" else "AM",
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25))
                        .clickable { showDatePicker = true }) {
                    val calendar = Calendar.getInstance()
                    selectedDateMillis?.let { calendar.timeInMillis = it }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)),
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.time)
                                .uppercase(),
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Text(
                            calendar.get(Calendar.YEAR).toString(),
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextButton({ onDismiss() }) {
                    Text("Cancel")
                }
                TextButton({
                    if (eventTitle.isNotEmpty()) {
                        if (isTask) {
                            val calendar = Calendar.getInstance()
                            selectedDateMillis?.let { calendar.timeInMillis = it }
                            calendar.set(Calendar.HOUR, selectedHour)
                            calendar.set(Calendar.MINUTE, selectedMinute)
                            calendar.set(Calendar.SECOND, 0)
                            calendar.set(Calendar.MILLISECOND, 0)
                            calendar.set(Calendar.AM_PM, if (selectedAfternoon) 1 else 0)
                            onSave(
                                Task(
                                    title = eventTitle,
                                    description = eventDescription,
                                    tags = eventTags,
                                    dueTime = if (isDue) calendar.timeInMillis else null,
                                    isCompleted = false,
                                )
                            )
                        } else onSave(
                            Note(
                                title = eventTitle,
                                description = eventDescription,
                                tags = eventTags,
                            )
                        )
                        onDismiss()
                    } else Toast.makeText(context, "Please enter a title", Toast.LENGTH_SHORT)
                        .show()
                }) {
                    Text("Save")
                }
            }
        }
    }
}