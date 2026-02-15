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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sorted.domain.model.PriorityFilter
import com.example.sorted.domain.model.Todo
import com.example.sorted.presentation.todo.composables.BottomFilterBar

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    var showPriorityMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // ===========================
        // MAIN TODO LIST
        // ===========================

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp), // space for floating bar
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                uiState.todos,
                key = { it.id }) { todo ->
                TodoItem(
                    todo = todo,
                    onDelete = { viewModel.deleteTodo(todo) }
                )
            }
        }

        // ===========================
        // FLOATING BOTTOM BAR
        // ===========================

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {

            BottomFilterBar(
                selectedStatus = uiState.statusFilter,
                onStatusSelected = { viewModel.changeStatusFilter(it) },
                onAddClick = {
                    // TODO: Navigate to Add Screen
                },
                onPriorityClick = {
                    showPriorityMenu = true
                }
            )
        }

        // ===========================
        // PRIORITY DROPDOWN
        // ===========================

        DropdownMenu(
            expanded = showPriorityMenu,
            onDismissRequest = { showPriorityMenu = false },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {

            PriorityFilter.entries.forEach { priority ->

                DropdownMenuItem(
                    text = {
                        Text(
                            text = priority.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        viewModel.changePriorityFilter(priority)
                        showPriorityMenu = false
                    }
                )
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                text = todo.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = todo.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Due: ${todo.dueDate}",
                    style = MaterialTheme.typography.labelSmall
                )

                TextButton(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}
