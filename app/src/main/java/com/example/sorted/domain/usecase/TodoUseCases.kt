package com.example.sorted.domain.usecase

data class TodoUseCases(
    val addTodo : AddTodoUseCase,
    val updateTodo : UpdateTodoUseCase,
    val deleteTodo : DeleteTodoUseCase,
    val getAllTodo : GetAllTodoUseCase
)