package com.example.sorted.data.local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sorted.data.local.dao.TodoDao
import com.example.sorted.data.local.Entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao() : TodoDao
}