package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.sorted.domain.model.FilterType
import com.example.sorted.presentation.todo.enumFunctions.displayName

@Composable
fun FilterNavigation() {
    var selectedFilter by remember { mutableStateOf(FilterType.BY_DUE_DATE_TODAY) }
    val filters = listOf(
        FilterType.BY_DUE_DATE_TODAY,
        FilterType.BY_DUE_DATE_COMPLETED,
        FilterType.BY_DUE_DATE_PENDING
    )
    Row {
        filters.forEach { filter ->
            FilterChip(
                onClick = { selectedFilter = filter },
                label = {
                    Text(
                        text = filter.displayName(),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                selected = selectedFilter == filter,
                leadingIcon = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterNavigationPreview() {
    MaterialTheme {
        FilterNavigation()
    }
}
