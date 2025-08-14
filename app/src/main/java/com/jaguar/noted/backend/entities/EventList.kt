package com.jaguar.noted.backend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "EventLists")
data class EventList(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val name: String,
    val emoji: String
)
