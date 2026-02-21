package com.example.sorted.presentation.todo

import com.example.sorted.domain.model.PriorityFilter
import com.example.sorted.domain.model.SortType
import com.example.sorted.domain.model.StatusFilter
import com.example.sorted.domain.model.Todo

data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val sortType: SortType = SortType.Date,
    val statusFilter: StatusFilter = StatusFilter.ALL,
    val priorityFilter: PriorityFilter = PriorityFilter.ALL,
    val titleError: String? = null,
    val dueDateError: String? = null,
    val isDarkTheme: Boolean = false
)