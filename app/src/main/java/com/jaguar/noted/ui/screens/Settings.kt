package com.jaguar.noted.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jaguar.noted.backend.DatabaseViewModel

@Composable
fun Settings(databaseViewModel: DatabaseViewModel, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("Lists")

    }
}