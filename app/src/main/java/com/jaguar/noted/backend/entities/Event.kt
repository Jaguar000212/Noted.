package com.jaguar.noted.backend.entities

import java.util.UUID

interface Event {
    val id: UUID
    val title: String
    val description: String
    val tags: List<String>
    val listId: UUID?
}
