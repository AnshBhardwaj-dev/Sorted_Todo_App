package com.example.sorted.data.repository

import com.example.sorted.data.local.dao.TodoDao
import com.example.sorted.data.mapper.TodoMapper
import com.example.sorted.domain.model.Todo
import com.example.sorted.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {
    override suspend fun insert(todo: Todo) {
        dao.insertTodo(TodoMapper.toEntity(todo))
    }

    override suspend fun update(todo: Todo) {
        dao.updateTodo(TodoMapper.toEntity(todo))
    }

    override suspend fun delete(todo: Todo) {
        dao.deleteTodo(TodoMapper.toEntity(todo))
    }

    override fun getAllTodos(): Flow<List<Todo>> {
        return dao.getAllTodos().map { emptyList ->
            emptyList.map { entity ->
                TodoMapper.toDomain(entity)
            }
        }
    }
}