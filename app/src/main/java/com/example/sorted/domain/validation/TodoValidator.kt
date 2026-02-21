package com.example.sorted.domain.validation

import com.example.sorted.domain.model.Todo
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class TodoValidator {

    fun validate(todo: Todo): ValidationResult {
        var titleError: String? = null
        var dueDateError: String? = null
        val success = "Successfully Added"
        if (todo.title.isBlank()) {
            titleError = "Title cannot be empty"
        }
        if (isPastDate(todo.dueDate)) {
            dueDateError = "Due date cannot be in the past"
        }
        return if (titleError != null || dueDateError != null) {
            ValidationResult.Error(
                titleError,
                dueDateError
            )
        } else (
                ValidationResult.Success(success)
                )
    }
}

private fun isPastDate(timestamp: Long): Boolean {
    val today = LocalDate.now()
    val taskDate = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return taskDate.isBefore(today)
}