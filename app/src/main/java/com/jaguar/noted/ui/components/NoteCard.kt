package com.jaguar.noted.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.jaguar.noted.backend.entities.Note
import com.jaguar.noted.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun NoteCard(note: Note, onCheckClick: () -> Unit, onClick: () -> Unit) {
    val isCompleted: Boolean = note.isCompleted

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if (isCompleted) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                if (isCompleted) "Completed" else "Not Completed",
                tint = if (isCompleted) Color.Green else Color.Unspecified,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onCheckClick() })
            Text(
                note.title,
                textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                color = if (isCompleted) Color.Gray else Color.Unspecified
            )
        }
        if (note.dueTime != null) Row(verticalAlignment = Alignment.CenterVertically) {
            val dueCalendar = Calendar.getInstance()
            dueCalendar.timeInMillis = note.dueTime
            val currentCalendar = Calendar.getInstance()
            currentCalendar.timeInMillis = System.currentTimeMillis()

            Text(
                if (dueCalendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR) && dueCalendar.get(
                        Calendar.YEAR
                    ) == currentCalendar.get(Calendar.YEAR)
                ) {
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(dueCalendar.time)
                        .uppercase()
                } else {
                    SimpleDateFormat("dd-MMM", Locale.getDefault()).format(dueCalendar.time)
                        .uppercase()
                },
                style = Typography.bodySmall,
                color = if (dueCalendar.timeInMillis < System.currentTimeMillis() && !isCompleted) Color.Red else Color.Gray
            )
            Icon(
                Icons.Outlined.Info,
                "Info",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(16.dp)
            )
        }
    }
}