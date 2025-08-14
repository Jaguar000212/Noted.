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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.jaguar.noted.backend.entities.Task
import com.jaguar.noted.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TaskCard(task: Task, onCheckClick: () -> Unit, onClick: () -> Unit) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if (task.isCompleted) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                if (task.isCompleted) "Completed" else "Not Completed",
                tint = if (task.isCompleted) Color.Green else LocalContentColor.current,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onCheckClick() })
            Text(
                task.title,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                color = if (task.isCompleted) Color.Gray else LocalContentColor.current.copy(alpha = 0.8f)
            )
        }
        if (task.dueTime != null) Row(verticalAlignment = Alignment.CenterVertically) {
            val dueCalendar = Calendar.getInstance()
            dueCalendar.timeInMillis = task.dueTime
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
                color = if (dueCalendar.timeInMillis < System.currentTimeMillis() && !task.isCompleted) Color.Red else Color.Gray
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