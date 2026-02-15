package com.example.sorted.domain.repository

import com.example.sorted.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insert(todo : Todo)
    suspend fun update(todo : Todo)
    suspend fun delete(todo : Todo)
    fun getAllTodos() : Flow<List<Todo>>
}