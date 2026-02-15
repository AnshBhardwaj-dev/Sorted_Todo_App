package com.example.sorted.domain.validation

import com.example.sorted.domain.model.Todo

class TodoValidator {

    fun validate(todo: Todo): ValidationResult {
        var titleError: String? = null
        var dueDateError: String? = null
        val success: String = "Successfully Added"
        if (todo.title.isBlank()) {
            titleError = "Title cannot be empty"
        }
        if (todo.dueDate < System.currentTimeMillis()) {
            dueDateError = "Due date cannot be in the past"
        }
        return if (titleError != null && dueDateError != null) {
            ValidationResult.Error(
                titleError,
                dueDateError
            )
        } else (
                ValidationResult.Success(success)
                )
    }
}