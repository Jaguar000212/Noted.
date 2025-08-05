package com.jaguar.noted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jaguar.noted.navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        addSampleNotes()
        setContent {
            Navigation()
        }
    }

//    private fun addSampleNotes() {
//        val notes = listOf(
//            Note(
//                title = "Sample Note 1",
//                description = "This is the description for Sample Note 1.",
//                tags = emptyList(),
//                dueDate = null,
//                dueTime = null,
//                isCompleted = false,
//                subTasks = emptyList()
//            ), Note(
//                "Sample Note 2",
//                "This is the description for Sample Note 2.",
//                emptyList(),
//                null,
//                null,
//                false,
//                emptyList()
//            ), Note(
//                "Sample Note 3",
//                "This is the description for Sample Note 3.",
//                emptyList(),
//                null,
//                "09:00",
//                true,
//                listOf(
//                    Note(
//                        title = "Sample Note 1",
//                        description = "This is the description for Sample Note 1.",
//                        tags = emptyList(),
//                        dueDate = null,
//                        dueTime = null,
//                        isCompleted = false,
//                        subTasks = emptyList()
//                    ), Note(
//                        "Sample Note 2",
//                        "This is the description for Sample Note 2.",
//                        emptyList(),
//                        null,
//                        null,
//                        false,
//                        emptyList()
//                    ), Note(
//                        "Sample Note 3",
//                        "This is the description for Sample Note 3.",
//                        emptyList(),
//                        null,
//                        "09:00",
//                        true,
//                        emptyList()
//                    ), Note(
//                        "Sample Note 4",
//                        "This is the description for Sample Note 4.",
//                        emptyList(),
//                        null,
//                        null,
//                        false,
//                        emptyList()
//                    ), Note(
//                        "Sample Note 5",
//                        "This is the description for Sample Note 5.",
//                        emptyList(),
//                        null,
//                        null,
//                        false,
//                        emptyList()
//                    ), Note(
//                        "Sample Note 6",
//                        "This is the description for Sample Note 6.",
//                        emptyList(),
//                        null,
//                        null,
//                        false,
//                        emptyList()
//                    ), Note(
//                        "Sample Note 7",
//                        "This is the description for Sample Note 7.",
//                        emptyList(),
//                        null,
//                        null,
//                        false,
//                        emptyList()
//                    )
//                )
//            ), Note(
//                "Sample Note 4",
//                "This is the description for Sample Note 4.",
//                emptyList(),
//                null,
//                null,
//                false,
//                emptyList()
//            ), Note(
//                "Sample Note 5",
//                "This is the description for Sample Note 5.",
//                emptyList(),
//                null,
//                null,
//                false,
//                emptyList()
//            ), Note(
//                "Sample Note 6",
//                "This is the description for Sample Note 6.",
//                emptyList(),
//                null,
//                null,
//                false,
//                emptyList()
//            ), Note(
//                "Sample Note 7",
//                "This is the description for Sample Note 7.",
//                emptyList(),
//                null,
//                null,
//                false,
//                emptyList()
//            )
//        )
//        NotesJSONSerializer("notes.json", context = this).saveNotes(notes)
//    }
}
