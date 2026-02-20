package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sorted.domain.model.Todo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoItem(
    existingTodo: Todo? = null,
    onSave: (String, String, Long?, String) -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit
) {

    var title by remember { mutableStateOf(existingTodo?.title ?: "") }
    var description by remember { mutableStateOf(existingTodo?.description ?: "") }
    var dueDate by remember { mutableStateOf(existingTodo?.dueDate) }
    var priority by remember { mutableStateOf(existingTodo?.priority?.name ?: "Low") }

    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dueDate
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        tonalElevation = 8.dp
    ) {

        Column(Modifier.padding(16.dp)) {

            Text(
                text = if (existingTodo == null) "New Task" else "Edit Task",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Title") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Description") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = dueDate?.let { convertMillisToDate(it) } ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Due Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Button(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        onSave(
                            title,
                            description,
                            dueDate,
                            priority
                        )
                    },
                    modifier = Modifier.weight(1f),
                    enabled = title.isNotBlank()
                ) {
                    Text(if (existingTodo == null) "Add Task" else "Update Task")
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        dueDate = datePickerState.selectedDateMillis
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat(
        "MM/dd/yyyy",
        Locale.getDefault()
    )
    return formatter.format(Date(millis))
}