package com.jaguar.noted.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jaguar.noted.backend.DatabaseViewModel
import com.jaguar.noted.backend.entities.EventList

@Composable
fun AddListDialog(databaseViewModel: DatabaseViewModel, onDismiss: () -> Unit) {
    var listName: String by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = listName,
            onValueChange = { listName = it },
            label = { Text("List") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button({
            databaseViewModel.addEventList(eventList = EventList(name = listName, emoji = "😊"))
            onDismiss()
        }, modifier = Modifier.fillMaxWidth()) { Text("Add") }
    }

}