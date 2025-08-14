package com.jaguar.noted.backend.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jaguar.noted.backend.entities.EventList
import kotlinx.coroutines.flow.Flow

@Dao
interface EventListDao {
    @Insert
    suspend fun insert(eventList: EventList)

    @Update
    suspend fun update(eventList: EventList)

    @Delete
    suspend fun delete(eventList: EventList)

    @Query("SELECT * FROM EventLists")
    fun getAll(): Flow<List<EventList>>
}