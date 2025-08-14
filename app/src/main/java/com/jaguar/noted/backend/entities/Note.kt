package com.jaguar.noted.backend.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "Notes", foreignKeys = [ForeignKey(
        entity = EventList::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Note(
    @PrimaryKey override val id: UUID = UUID.randomUUID(),
    override val title: String,
    override val description: String,
    override val tags: List<String> = emptyList(),
    val listId: UUID? = null
) : Event
