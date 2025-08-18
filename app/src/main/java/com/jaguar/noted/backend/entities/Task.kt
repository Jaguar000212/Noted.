package com.jaguar.noted.backend.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "Tasks", foreignKeys = [ForeignKey(
        entity = EventList::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey override val id: UUID = UUID.randomUUID(),
    override val title: String,
    override val description: String,
    override val tags: List<String> = emptyList(),
    val dueTime: Long?,
    val isCompleted: Boolean,
    override val listId: UUID? = null
) : Event {
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
