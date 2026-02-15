package com.example.sorted.domain.usecase

import com.example.sorted.domain.model.FilterType
import com.example.sorted.domain.model.PRIORITY
import com.example.sorted.domain.model.SortType
import com.example.sorted.domain.model.Todo
import com.example.sorted.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllTodoUseCase(
    private val repository: TodoRepository
) {
    operator fun invoke(
        sortType: SortType,
        filterType: FilterType
    ): Flow<List<Todo>> {
        return repository.getAllTodos().map { todos ->
            val filtered = when (filterType) {
                FilterType.BY_ALL -> todos
                FilterType.BY_DUE_DATE_COMPLETED -> todos.filter { it.isCompleted }
                FilterType.BY_DUE_DATE_PENDING -> todos.filter { !it.isCompleted }
                FilterType.BY_DUE_DATE_TODAY -> todos.filter { it.dueDate == System.currentTimeMillis() }
                FilterType.BY_DUE_DATE_UPCOMING -> todos.filter { it.dueDate > System.currentTimeMillis() }
                FilterType.BY_PRIORITY_HIGH -> todos.sortedByDescending { it.priority == PRIORITY.HIGH }
                FilterType.BY_PRIORITY_LOW -> todos.sortedByDescending { it.priority == PRIORITY.LOW }
                FilterType.BY_PRIORITY_MEDIUM -> todos.sortedByDescending { it.priority == PRIORITY.MEDIUM }
            }
            when (sortType) {
                SortType.BY_DUE_DATE_ASC -> filtered.sortedBy { it.dueDate }
                SortType.BY_DUE_DATE_DESC -> filtered.sortedByDescending { it.dueDate }
                SortType.BY_PRIORITY_ASC -> filtered.sortedBy { it.priority.ordinal }
            }
        }
    }
}