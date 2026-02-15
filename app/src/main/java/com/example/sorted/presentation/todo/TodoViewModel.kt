package com.example.sorted.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sorted.domain.model.FilterType
import com.example.sorted.domain.model.SortType
import com.example.sorted.domain.model.Todo
import com.example.sorted.domain.usecase.TodoUseCases
import com.example.sorted.domain.validation.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoUIState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            todoUseCases.getAllTodo(
                sortType = _uiState.value.sortType,
                filterType = _uiState.value.filterType
            ).collect { todos ->
                _uiState.update { it.copy(todos = todos) }
            }
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            when (val result = todoUseCases.addTodo(todo)) {
                is ValidationResult.Success -> {
                    _uiState.update {
                        it.copy(
                            titleError = null,
                            dueDateError = null
                        )
                    }
                }

                is ValidationResult.Error -> {
                    _uiState.update {
                        it.copy(
                            titleError = result.titleError,
                            dueDateError = result.dueDateError
                        )
                    }
                }
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoUseCases.deleteTodo(todo)
        }
    }

    fun changeSorting(sortType: SortType) {
        _uiState.update { it.copy(sortType = sortType) }
        loadTodos()
    }

    fun changeFilter(filterType: FilterType) {
        _uiState.update { it.copy(filterType = filterType) }
        loadTodos()
    }
}