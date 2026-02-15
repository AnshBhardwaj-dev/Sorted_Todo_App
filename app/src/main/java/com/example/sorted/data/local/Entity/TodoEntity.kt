package com.example.sorted.data.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val dueDate: Long,
    val isCompleted: Boolean,

    // Sorted as String in DB
    val priority: String
)