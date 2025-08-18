package com.jaguar.noted.backend.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jaguar.noted.backend.entities.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM Tasks")
    fun getAll(): Flow<List<Task>>

    @Query("SELECT * FROM Tasks WHERE listId = :lId")
    fun getTasksByList(lId: UUID): Flow<List<Task>>
}