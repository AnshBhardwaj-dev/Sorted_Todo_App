package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sorted.R
import com.example.sorted.domain.model.StatusFilter
import com.example.sorted.ui.theme.SortedTheme

@Composable
fun BottomFilterBar(
    selectedStatus: StatusFilter,
    onStatusSelected: (StatusFilter) -> Unit,
    onAddClick: () -> Unit,
    onPriorityClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            shape = RoundedCornerShape(28.dp),
            tonalElevation = 8.dp,
            shadowElevation = 12.dp,
            color = MaterialTheme.colorScheme.surface
        ) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                FilterNavigation(
                    selectedFilter = selectedStatus,
                    onFilterSelected = onStatusSelected
                )

                IconButton(
                    onClick = onPriorityClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_filter_alt_24),
                        contentDescription = "Priority Filter",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = (-28).dp, x = (-20).dp),
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomFilterBarPreview() {
    MaterialTheme(){
        BottomFilterBar(
            selectedStatus = StatusFilter.ALL,
            onStatusSelected = {},
            onAddClick = {},
            onPriorityClick = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
fun BottomFilterBarDarkPreview() {
    MaterialTheme(colorScheme = darkColorScheme()){
        BottomFilterBar(
            selectedStatus = StatusFilter.ALL,
            onStatusSelected = {},
            onAddClick = {},
            onPriorityClick = {}
        )
    }
}
