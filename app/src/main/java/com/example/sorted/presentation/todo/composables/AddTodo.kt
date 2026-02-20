package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    // =========================
    // PREFILL IF EDITING
    // =========================

    var title by remember { mutableStateOf(existingTodo?.title ?: "") }
    var description by remember { mutableStateOf(existingTodo?.description ?: "") }
    var dueDate by remember { mutableStateOf<Long?>(existingTodo?.dueDate) }
    var priority by remember { mutableStateOf(existingTodo?.priority?.name ?: "Low") }

    var showDateModal by remember { mutableStateOf(false) }

    val options = listOf(
        "Low",
        "Medium",
        "High"
    )

    var selectedIndex by remember {
        mutableIntStateOf(
            options.indexOf(priority).coerceAtLeast(0)
        )
    }

    val superscriptStyle = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.error
    )

    Box(
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onSurface,
            border = BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                // =========================
                // HEADER
                // =========================

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (existingTodo == null) "New Task" else "Edit Task",
                        style = MaterialTheme.typography.titleMedium
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close"
                        )
                    }
                }

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))

                // =========================
                // TITLE
                // =========================

                Text(
                    buildAnnotatedString {
                        append("Title")
                        withStyle(superscriptStyle) { append("*") }
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Title") },
                    supportingText = { Text("Enter task title...") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // =========================
                // DESCRIPTION
                // =========================

                Text("Description (Optional)")

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Description") },
                    supportingText = { Text("Add a description...") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // =========================
                // DUE DATE
                // =========================

                Text("Due Date")

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = dueDate?.let { convertMillisToDate(it) } ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select date") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDateModal = true }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // =========================
                // PRIORITY
                // =========================

                Text("Priority")

                Spacer(modifier = Modifier.height(4.dp))

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = {
                                selectedIndex = index
                                priority = label
                            },
                            selected = index == selectedIndex,
                            label = { Text(label) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // =========================
                // ACTION BUTTONS
                // =========================

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

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
                        Text(
                            if (existingTodo == null)
                                "Add Task"
                            else
                                "Update Task"
                        )
                    }
                }
            }
        }
    }

    // =========================
    // DATE PICKER MODAL
    // =========================

    if (showDateModal) {
        DatePickerModal(
            initialDate = dueDate,
            onDateSelected = { selected ->
                dueDate = selected
            },
            onDismiss = { showDateModal = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    initialDate: Long? = null,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat(
        "MM/dd/yyyy",
        Locale.getDefault()
    )
    return formatter.format(Date(millis))
}