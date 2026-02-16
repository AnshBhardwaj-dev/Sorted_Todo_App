package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sorted.domain.model.StatusFilter

@Composable
fun FilterNavigation(
    selectedFilter: StatusFilter,
    onFilterSelected: (StatusFilter) -> Unit
) {

    val filters = listOf(
        StatusFilter.TODAY to "TODAY",
        StatusFilter.COMPLETED to "COMPLETED",
        StatusFilter.PENDING to "PENDING"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        filters.forEach { (filter, label) ->

            val isSelected = selectedFilter == filter

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
                    .wrapContentSize()
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (isSelected)
                            MaterialTheme.colorScheme.inverseSurface
                        else
                            MaterialTheme.colorScheme.surface
                    )
                    .clickable { onFilterSelected(filter) }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected)
                        MaterialTheme.colorScheme.inverseOnSurface
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
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