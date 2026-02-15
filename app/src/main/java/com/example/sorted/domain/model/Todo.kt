package com.example.sorted.domain.model

data class Todo(
    val title : String = "",
    val description : String = "",
    val dueDate : Long,
    val isCompleted : Boolean = false,
    val priority : PRIORITY,
    val id : Long = 0
)
