package com.example.sorted.domain.usecase

import com.example.sorted.domain.model.Todo
import com.example.sorted.domain.repository.TodoRepository

class DeleteTodoUseCase(
    private val repository: TodoRepository
) {
    operator suspend fun invoke(todo: Todo) {
        repository.delete(todo)
    }
}