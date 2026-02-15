package com.example.sorted.domain.usecase

import com.example.sorted.domain.model.PriorityFilter
import com.example.sorted.domain.model.SortType
import com.example.sorted.domain.model.StatusFilter
import com.example.sorted.domain.model.Todo
import com.example.sorted.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllTodoUseCase(
    private val repository: TodoRepository
) {
    operator fun invoke(
        sortType: SortType,
        statusFilter: StatusFilter,
        priorityFilter: PriorityFilter
    ): Flow<List<Todo>> {
        return repository.getAllTodos().map { todos ->
            val statusFiltered = when (statusFilter) {
                StatusFilter.ALL -> todos
                StatusFilter.COMPLETED -> todos.filter { it.isCompleted }
                StatusFilter.PENDING -> todos.filter { !it.isCompleted }
                StatusFilter.TODAY -> {
                    val now = System.currentTimeMillis()
                    todos.filter { it.dueDate >= now }
                }
            }
            val priorityFiltered = when (priorityFilter) {
                PriorityFilter.ALL -> statusFiltered
                PriorityFilter.HIGH -> statusFiltered.filter { it.priority.name == "HIGH" }
                PriorityFilter.MEDIUM -> statusFiltered.filter { it.priority.name == "MEDIUM" }
                PriorityFilter.LOW -> statusFiltered.filter { it.priority.name == "LOW" }
            }
            when (sortType) {
                SortType.BY_PRIORITY_ASC ->
                    priorityFiltered.sortedBy { it.priority.ordinal }

                SortType.BY_DUE_DATE_ASC ->
                    priorityFiltered.sortedBy { it.dueDate }

                SortType.BY_DUE_DATE_DESC ->
                    priorityFiltered.sortedByDescending { it.dueDate }
            }
        }
    }
}