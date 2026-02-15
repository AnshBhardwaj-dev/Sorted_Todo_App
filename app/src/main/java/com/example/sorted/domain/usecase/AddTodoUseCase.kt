package com.example.sorted.domain.usecase

import com.example.sorted.domain.model.Todo
import com.example.sorted.domain.repository.TodoRepository
import com.example.sorted.domain.validation.TodoValidator
import com.example.sorted.domain.validation.ValidationResult

class AddTodoUseCase(
    private val repository: TodoRepository,
    private val validator: TodoValidator
) {

    operator suspend fun invoke(todo: Todo): ValidationResult {
        val validatorResult = validator.validate(todo)
        if (validatorResult is ValidationResult.Success) {
            repository.insert(todo)
            return validatorResult
        } else return validatorResult
    }
}