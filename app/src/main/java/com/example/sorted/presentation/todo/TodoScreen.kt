package com.example.sorted.presentation.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sorted.R
import com.example.sorted.domain.model.PRIORITY
import com.example.sorted.domain.model.PriorityFilter
import com.example.sorted.domain.model.Todo
import com.example.sorted.presentation.todo.composables.AddTodoItem
import com.example.sorted.presentation.todo.composables.BottomFilterBar
import com.example.sorted.presentation.todo.composables.StatCard
import com.example.sorted.presentation.todo.composables.SwipeToReveal
import com.example.sorted.presentation.todo.composables.getTodayDate

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    var showPriorityMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    var showAddEditDialog by remember { mutableStateOf(false) }
    var editingTodo by remember { mutableStateOf<Todo?>(null) }

    val allTodos = uiState.todos

    val pendingCount = allTodos.count { !it.isCompleted }
    val highCount = allTodos.count { it.priority == PRIORITY.HIGH }
    val finishedCount = allTodos.count { it.isCompleted }

    val efficiency = if (allTodos.isNotEmpty()) {
        ((finishedCount.toFloat() / allTodos.size) * 100).toInt()
    } else 0

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Column {

                    Text(
                        text = "Sorted.",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "PRECISION PRODUCTIVITY",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatCard(
                            "PENDING",
                            pendingCount.toString()
                        )
                        StatCard(
                            "HIGH",
                            highCount.toString()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatCard(
                            "FINISHED",
                            finishedCount.toString()
                        )
                        StatCard(
                            "EFFICIENCY",
                            "$efficiency%"
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Agenda",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            getTodayDate(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Search tasks...") },
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        IconButton(
                            onClick = { showPriorityMenu = true }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.outline_sort_24),
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            val filteredTodos = allTodos.filter {
                it.title.contains(
                    searchQuery,
                    true
                )
            }

            items(
                filteredTodos,
                key = { it.id }) { todo ->

                SwipeToReveal(
                    title = todo.title,
                    description = todo.description,
                    dueDate = todo.dueDate,
                    priority = todo.priority.name.lowercase()
                        .replaceFirstChar { it.uppercase() },
                    onDelete = { viewModel.deleteTodo(todo) },
                    onEdit = {
                        editingTodo = todo
                        showAddEditDialog = true
                    },
                    onDone = { viewModel.markDone(todo) }
                )
            }
        }

        BottomFilterBar(
            selectedStatus = uiState.statusFilter,
            onStatusSelected = { viewModel.changeStatusFilter(it) },
            onAddClick = {
                editingTodo = null
                showAddEditDialog = true
            },
            onPriorityClick = { showPriorityMenu = true },
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        DropdownMenu(
            expanded = showPriorityMenu,
            onDismissRequest = { showPriorityMenu = false },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            PriorityFilter.entries.forEach { priority ->
                DropdownMenuItem(
                    text = { Text(priority.name) },
                    onClick = {
                        viewModel.changePriorityFilter(priority)
                        showPriorityMenu = false
                    }
                )
            }
        }

        if (showAddEditDialog) {
            AddTodoItem(
                existingTodo = editingTodo,
                onDismiss = { showAddEditDialog = false },
                onCancel = { showAddEditDialog = false },
                onSave = { title, description, dueDate, priority ->

                    if (editingTodo == null) {
                        viewModel.addTodo(
                            Todo(
                                id = 0,
                                title = title,
                                description = description,
                                dueDate = dueDate ?: System.currentTimeMillis(),
                                priority = PRIORITY.valueOf(priority.uppercase()),
                                isCompleted = false
                            )
                        )
                    } else {
                        viewModel.updateTodo(
                            editingTodo!!.copy(
                                title = title,
                                description = description,
                                dueDate = dueDate ?: editingTodo!!.dueDate,
                                priority = PRIORITY.valueOf(priority.uppercase())
                            )
                        )
                    }

                    showAddEditDialog = false
                }
            )
        }
    }
}