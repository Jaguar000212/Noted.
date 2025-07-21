package com.jaguar.noted.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.jaguar.noted.objects.Note

@Composable
fun NoteCard(note: Note, onCheckClick: () -> Unit, onClick: () -> Unit) {
    val isCompleted: Boolean = note.getIsCompleted()

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
            Text(note.getTitle())
        }
        if (note.getDueTime() != null) Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Outlined.Info, "Due Time", modifier = Modifier.padding(8.dp)
            )
            Text(note.getDueTime()!!)

        }
    }
}