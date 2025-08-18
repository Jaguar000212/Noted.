package com.jaguar.noted.backend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jaguar.noted.backend.daos.EventListDao
import com.jaguar.noted.backend.daos.NoteDao
import com.jaguar.noted.backend.daos.TaskDao
import com.jaguar.noted.backend.entities.EventList
import com.jaguar.noted.backend.entities.Note
import com.jaguar.noted.backend.entities.Task

@Database(
    entities = [Task::class, Note::class, EventList::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NotedDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun noteDao(): NoteDao
    abstract fun eventListDao(): EventListDao

    companion object {
        @Volatile
        private var INSTANCE: NotedDatabase? = null
        fun getDatabase(context: Context): NotedDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotedDatabase::class.java,
                    "NotedDatabase",
                ).fallbackToDestructiveMigration(
                    true
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}