package com.example.sorted.data.mapper

import com.example.sorted.data.local.Entity.TodoEntity
import com.example.sorted.domain.model.PRIORITY
import com.example.sorted.domain.model.Todo

object TodoMapper {

    fun toDomain(entity: TodoEntity): Todo {
        return Todo(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            isCompleted = entity.isCompleted,
            dueDate = entity.dueDate,
            priority = PRIORITY.valueOf(entity.priority)
        )
    }

    fun toEntity(todo: Todo): TodoEntity {
        return TodoEntity(
            id = todo.id,
            title = todo.title,
            description = todo.description,
            dueDate = todo.dueDate,
            isCompleted = todo.isCompleted,
            priority = todo.priority.name
        )
    }
}