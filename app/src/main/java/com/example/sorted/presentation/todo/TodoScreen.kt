package com.example.sorted.presentation.todo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sorted.R
import com.example.sorted.domain.model.PRIORITY
import com.example.sorted.domain.model.PriorityFilter
import com.example.sorted.domain.model.SortType
import com.example.sorted.domain.model.Todo
import com.example.sorted.presentation.todo.composables.AddTodoItem
import com.example.sorted.presentation.todo.composables.BottomFilterBar
import com.example.sorted.presentation.todo.composables.StatCard
import com.example.sorted.presentation.todo.composables.SwipeToReveal
import com.example.sorted.presentation.todo.composables.getTodayDate
import com.example.sorted.ui.theme.displayFontFamily

@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel(),
    onThemeToggle: () -> Unit,
    isDarkTheme: Boolean
) {

    val uiState by viewModel.uiState.collectAsState()

    var showPriorityMenu by remember { mutableStateOf(false) }
    var showSortMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    var showAddEditDialog by remember { mutableStateOf(false) }
    var editingTodo by remember { mutableStateOf<Todo?>(null) }

    val filteredTodos = uiState.todos
    val allTodos by viewModel.allTodos.collectAsState()

    val pendingCount = allTodos.count { !it.isCompleted }
    val highCount = allTodos.count { it.priority == PRIORITY.HIGH }
    val finishedCount = allTodos.count { it.isCompleted }

    val efficiency = if (allTodos.isNotEmpty()) {
        ((finishedCount.toFloat() / allTodos.size) * 100).toInt()
    } else 0

    val searchFilteredTodos = filteredTodos.filter {
        it.title.contains(
            searchQuery,
            ignoreCase = true
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                )
            )
    ) {
        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets.safeGestures,
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // HEADER
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "Sorted",
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = displayFontFamily,
                                fontSize = 40.sp,
                                letterSpacing = (-0.02).sp
                            )
                            Text(
                                text = ".",
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.secondary,
                                fontFamily = displayFontFamily,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "PRECISION PRODUCTIVITY",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            letterSpacing = 3.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        tonalElevation = 2.dp,
                        shadowElevation = 1.dp
                    ) {
                        IconButton(onClick = onThemeToggle) {
                            Icon(
                                painter = painterResource(
                                    id = if (isDarkTheme) R.drawable.baseline_light_mode_24
                                    else R.drawable.baseline_dark_mode_24
                                ),
                                contentDescription = null
                            )
                        }
                    }


                }

                // STATS
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StatCard(
                        "PENDING",
                        pendingCount.toString(),
                        Modifier.weight(1f)
                    )
                    StatCard(
                        "HIGH",
                        highCount.toString(),
                        Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StatCard(
                        "FINISHED",
                        finishedCount.toString(),
                        Modifier.weight(1f)
                    )
                    StatCard(
                        "EFFICIENCY",
                        "$efficiency%",
                        Modifier.weight(1f)
                    )
                }


                // AGENDA + SEARCH
                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Agenda",
                            style = MaterialTheme.typography.headlineLarge,  // was titleLarge
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = displayFontFamily,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 32.sp,
                            letterSpacing = (-0.01).sp
                        )
                        Text(
                            getTodayDate(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            placeholder = { Text("Search tasks…") },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary.copy(alpha = .7f)
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(18.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = .5f),
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Box {
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.outlineVariant
                                ),
                                modifier = Modifier.size(56.dp)
                            ) {
                                IconButton(onClick = { showSortMenu = true }) {
                                    Icon(
                                        painter = painterResource(R.drawable.outline_sort_24),
                                        contentDescription = null
                                    )
                                }
                            }


                            DropdownMenu(
                                expanded = showSortMenu,
                                onDismissRequest = { showSortMenu = false }) {
                                SortType.entries.forEach { sortType ->
                                    DropdownMenuItem(
                                        text = { Text(sortType.name) },
                                        onClick = {
                                            viewModel.changeSorting(sortType)
                                            showSortMenu = false
                                        })
                                }
                            }
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = 4.dp,
                        bottom = 120.dp,   // clears the floating filter bar
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // TODOS
                    items(
                        searchFilteredTodos,
                        key = { it.id }) { todo ->
                        SwipeToReveal(
                            title = todo.title,
                            description = todo.description,
                            dueDate = todo.dueDate,
                            priority = todo.priority.name,
                            onDelete = { viewModel.deleteTodo(todo) },
                            onEdit = {
                                editingTodo = todo
                                showAddEditDialog = true
                            },
                            onDone = { viewModel.markDone(todo) }
                        )
                    }
                }
            }

            // PRIORITY DROPDOWN
            DropdownMenu(
                expanded = showPriorityMenu,
                onDismissRequest = { showPriorityMenu = false }) {
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

            // ADD / EDIT DIALOG (ADAPTIVE WIDTH)
            if (showAddEditDialog) {

                val screenWidth = LocalConfiguration.current.screenWidthDp
                val dialogModifier = if (screenWidth > 600) Modifier.fillMaxWidth(0.6f)
                else Modifier.fillMaxWidth(0.95f)

                Dialog(
                    properties = DialogProperties(
                        usePlatformDefaultWidth = false,
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    ),
                    onDismissRequest = { showAddEditDialog = false }) {
                    Box(modifier = dialogModifier) {

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
        }
        BottomFilterBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedStatus = uiState.statusFilter,
            onStatusSelected = { viewModel.changeStatusFilter(it) },
            onAddClick = {
                editingTodo = null
                showAddEditDialog = true
            },
            onPriorityClick = { showPriorityMenu = true }
        )
    }
}