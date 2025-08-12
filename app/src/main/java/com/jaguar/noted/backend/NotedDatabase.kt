package com.jaguar.noted.backend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jaguar.noted.backend.daos.TaskDao
import com.jaguar.noted.backend.entities.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NotedDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: NotedDatabase? = null
        fun getDatabase(context: Context): NotedDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotedDatabase::class.java,
                    "NotedDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}