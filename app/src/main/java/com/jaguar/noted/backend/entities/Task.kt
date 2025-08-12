package com.jaguar.noted.backend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val tags: List<String> = emptyList(),
    val dueTime: Long?,
    val isCompleted: Boolean,
    val listId: UUID? = null
) {
    override fun toString(): String {
        return """
            ID: $id
            Title: $title
            Description: $description
            Tags: $tags
            Due Time: $dueTime
            Is Completed: $isCompleted
            List ID: $listId
            """.trimIndent()
    }
}
