package com.example.sorted.presentation.todo

import com.example.sorted.domain.model.FilterType
import com.example.sorted.domain.model.SortType
import com.example.sorted.domain.model.Todo

data class TodoUIState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val sortType: SortType = SortType.BY_DUE_DATE_DESC,
    val filterType: FilterType = FilterType.BY_ALL,
    val titleError: String? = null,
    val dueDateError: String? = null
)