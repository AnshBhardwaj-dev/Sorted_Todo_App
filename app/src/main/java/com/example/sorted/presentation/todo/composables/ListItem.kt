package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sorted.ui.theme.SortedTheme

@Composable
fun TodoItem(
    title: String,
    description: String? = null,
    dueDate: Long,
    priority: String
) {
    var expanded by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(10),
        tonalElevation = 1.dp,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            // PRIORITY BADGE
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = priority,
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                        .background(
                            when (priority) {
                                "High" -> {
                                    MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                                }

                                "Medium" -> {
                                    MaterialTheme.colorScheme.primary
                                }

                                else -> {
                                    MaterialTheme.colorScheme.secondary
                                }
                            },
                            shape = RoundedCornerShape(4.dp)
                        ),
                    color = when (priority) {
                        "High" -> {
                            MaterialTheme.colorScheme.onError
                        }

                        "Medium" -> {
                            MaterialTheme.colorScheme.onPrimary
                        }

                        else -> {
                            MaterialTheme.colorScheme.onSecondary
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // CONTENT
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 44.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                )
                if (expanded && !description.isNullOrBlank()) {
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Left
                    )
                }
            }

            // DUEDATE & EXPAND/COLLAPSE
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Due: ${dueDate.toString()}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    if (expanded) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = if (expanded) "Collapse" else "Expand",
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TodoItemPreview() {
    SortedTheme {
        TodoItem(
            title = "Todo Item Title",
            description = "This is a description for the todo item.",
            dueDate = System.currentTimeMillis(),
            priority = "High"
        )
    }
}