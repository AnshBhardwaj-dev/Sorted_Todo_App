package com.example.sorted.presentation.todo.enumFunctions

import com.example.sorted.domain.model.PRIORITY


fun PRIORITY.displayName(): String {
    return when (this) {
        PRIORITY.LOW -> "Low"
        PRIORITY.MEDIUM -> "Medium"
        PRIORITY.HIGH -> "High"
    }
}