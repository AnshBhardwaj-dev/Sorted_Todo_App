package com.example.sorted.presentation.todo.enumFunctions

import com.example.sorted.domain.model.FilterType

fun FilterType.displayName(): String {
    return when (this) {
        FilterType.BY_DUE_DATE_TODAY -> "TODAY"
        FilterType.BY_DUE_DATE_COMPLETED -> "COMPLETED"
        FilterType.BY_DUE_DATE_PENDING -> "PENDING"
        FilterType.BY_DUE_DATE_UPCOMING -> "UPCOMING"
        FilterType.BY_ALL -> "ALL"
        FilterType.BY_PRIORITY_HIGH -> "HIGH"
        FilterType.BY_PRIORITY_MEDIUM -> "MEDIUM"
        FilterType.BY_PRIORITY_LOW -> "LOW"
    }
}