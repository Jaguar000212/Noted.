package com.jaguar.noted.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaguar.noted.backend.entities.Note
import com.jaguar.noted.ui.theme.Typography

@Composable
fun NoteCard(note: Note, onClick: () -> Unit) {
    Card({ onClick() }, modifier = Modifier.height(175.dp)) {
        Text(note.title, style = Typography.titleMedium, modifier = Modifier.padding(8.dp))
        Text(note.description,
            style = Typography.bodyMedium,
            color = LocalContentColor.current.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 6
        )
    }
}