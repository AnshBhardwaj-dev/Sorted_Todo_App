package com.example.sorted.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sorted.domain.model.PriorityFilter
import com.example.sorted.domain.model.SortType
import com.example.sorted.domain.model.StatusFilter
import com.example.sorted.domain.model.Todo
import com.example.sorted.domain.usecase.TodoUseCases
import com.example.sorted.domain.validation.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val useCases: TodoUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoUiState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TodoUiState> =
        combine(
            _uiState.map { it.sortType },
            _uiState.map { it.statusFilter },
            _uiState.map { it.priorityFilter }
        ) { sortType, statusFilter, priorityFilter ->
            Triple(
                sortType,
                statusFilter,
                priorityFilter
            )
        }.flatMapLatest { (sortType, statusFilter, priorityFilter) ->
            useCases.getAllTodo(
                sortType,
                statusFilter,
                priorityFilter
            )
        }.map { todos ->
            _uiState.value.copy(todos = todos)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _uiState.value
        )

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            useCases.getAllTodo(
                sortType = _uiState.value.sortType,
                statusFilter = _uiState.value.statusFilter,
                priorityFilter = _uiState.value.priorityFilter
            ).collect { todos ->
                _uiState.update { it.copy(todos = todos) }
            }
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            when (val result = useCases.addTodo(todo)) {

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

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            when (val result = useCases.updateTodo(todo)) {

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

    fun markDone(todo: Todo) {
        viewModelScope.launch {
            useCases.updateTodo(
                todo.copy(isCompleted = true)
            )
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            useCases.deleteTodo(todo)
        }
    }

    fun changeSorting(sortType: SortType) {
        _uiState.update { it.copy(sortType = sortType) }
    }

    fun changeStatusFilter(statusFilter: StatusFilter) {
        _uiState.update { it.copy(statusFilter = statusFilter) }
    }

    fun changePriorityFilter(priorityFilter: PriorityFilter) {
        _uiState.update { it.copy(priorityFilter = priorityFilter) }
    }
}