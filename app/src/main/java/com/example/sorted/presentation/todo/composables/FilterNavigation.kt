package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sorted.domain.model.StatusFilter

@Composable
fun FilterNavigation(
    selectedFilter: StatusFilter,
    onFilterSelected: (StatusFilter) -> Unit
) {

    val filters = listOf(
        StatusFilter.TODAY,
        StatusFilter.COMPLETED,
        StatusFilter.PENDING
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        filters.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        text = filter.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                shape = RoundedCornerShape(50),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    selectedLabelColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterNavigationPreview() {
    MaterialTheme {
        FilterNavigation(
            selectedFilter = StatusFilter.TODAY,
            onFilterSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterNavigationDARKPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        FilterNavigation(
            selectedFilter = StatusFilter.TODAY,
            onFilterSelected = {}
        )
    }
}