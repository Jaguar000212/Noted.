package com.jaguar.noted.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
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
import com.jaguar.noted.backend.entities.EventList
import com.jaguar.noted.backend.entities.Note
import com.jaguar.noted.backend.entities.Task
import com.jaguar.noted.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewEventBottomSheet(
    event: Event,
    eventLists: List<EventList>,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onSave: (Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val dueTime = if (event is Task) event.dueTime else null
    val isTask = event is Task
    var isDueSet: Boolean by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    var eventTitle by remember { mutableStateOf(event.title) }
    var eventDescription by remember { mutableStateOf(event.description) }
    var eventTags by remember { mutableStateOf(event.tags) }
    var eventList: EventList by remember {
        mutableStateOf(eventLists.find { it.id == event.listId } ?: eventLists[0])
    }

    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(is24Hour = false)
    var showTimePicker by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    var selectedDateMillis by remember { mutableStateOf(dueTime) }
    selectedDateMillis?.let { calendar.timeInMillis = it }
    var selectedHour by remember { mutableIntStateOf(calendar.get(Calendar.HOUR)) }
    var selectedMinute by remember { mutableIntStateOf(calendar.get(Calendar.MINUTE)) }
    var selectedAfternoon by remember { mutableStateOf(calendar.get(Calendar.AM_PM) == 1) }

    if (isTask) {
        if (showDatePicker) DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        selectedDateMillis = datePickerState.selectedDateMillis
                        isDueSet = true
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
                        isDueSet = true
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
            Text("Event", style = Typography.titleLarge)

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
                            if (!isDueSet && dueTime == null) "-" else String.format(
                                Locale.getDefault(), "%02d", selectedHour
                            ), style = Typography.titleLarge, modifier = Modifier.padding(8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            if (!isDueSet && dueTime == null) "-" else String.format(
                                Locale.getDefault(), "%02d", selectedMinute
                            ), style = Typography.titleLarge, modifier = Modifier.padding(8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Text(
                            if (!isDueSet && dueTime == null) "-" else if (selectedAfternoon) "PM" else "AM",
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25))
                        .clickable { showDatePicker = true }) {
                    selectedDateMillis?.let { calendar.timeInMillis = it }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            if (!isDueSet && dueTime == null) "-" else String.format(
                                Locale.getDefault(), "%02d", calendar.get(Calendar.DAY_OF_MONTH)
                            ), style = Typography.titleLarge, modifier = Modifier.padding(8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            if (!isDueSet && dueTime == null) "-" else SimpleDateFormat(
                                "MMM", Locale.getDefault()
                            ).format(calendar.time).uppercase(),
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Text(
                            if (!isDueSet && dueTime == null) "-" else calendar.get(Calendar.YEAR)
                                .toString(),
                            style = Typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
            ) {
                OutlinedButton(onClick = { expanded = true }) {
                    Text(eventList.emoji, modifier = Modifier.padding(8.dp))
                    Text(eventList.name)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    eventLists.forEach {
                        DropdownMenuItem(text = {
                            Text(
                                it.name, modifier = Modifier.padding(8.dp)
                            )
                        }, leadingIcon = { Text(it.emoji) }, onClick = {
                            eventList = it
                            expanded = false
                        })
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    TextButton({
                        onDelete()
                        onDismiss()
                    }) {
                        Text("Delete")
                    }
                    TextButton({
                        if (eventTitle.isNotEmpty()) {
                            if (event is Task) {
                                selectedDateMillis?.let { calendar.timeInMillis = it }
                                calendar.set(Calendar.HOUR, selectedHour)
                                calendar.set(Calendar.MINUTE, selectedMinute)
                                calendar.set(Calendar.SECOND, 0)
                                calendar.set(Calendar.MILLISECOND, 0)
                                calendar.set(Calendar.AM_PM, if (selectedAfternoon) 1 else 0)
                                onSave(
                                    event.copy(
                                        title = eventTitle,
                                        description = eventDescription,
                                        tags = eventTags,
                                        dueTime = if (isDueSet) calendar.timeInMillis else event.dueTime,
                                        isCompleted = false,
                                        listId = eventList.id
                                    )
                                )
                            } else if (event is Note) onSave(
                                event.copy(
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
}