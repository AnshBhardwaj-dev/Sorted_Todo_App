package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.sorted.R
import com.example.sorted.domain.model.StatusFilter

@Composable
fun BottomFilterBar(
    selectedStatus: StatusFilter,
    onStatusSelected: (StatusFilter) -> Unit,
    onAddClick: () -> Unit,
    onPriorityClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(64.dp)
                    .dropShadow(
                        shape = RoundedCornerShape(50),
                        shadow = Shadow(
                            radius = 10.dp,
                            spread = 2.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            offset = DpOffset(
                                3.dp,
                                4.dp
                            )
                        )
                    ),
                shape = RoundedCornerShape(50),
                color = Color.White.copy(alpha = 0.38f),
                tonalElevation = 8.dp
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = onPriorityClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_filter_alt_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    VerticalDivider(
                        modifier = Modifier
                            .height(28.dp)
                            .padding(horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    FilterNavigation(
                        selectedFilter = selectedStatus,
                        onFilterSelected = onStatusSelected
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // FAB
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.size(64.dp),
                elevation = FloatingActionButtonDefaults.loweredElevation(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BottomFilterBarPreview() {
    MaterialTheme() {
        BottomFilterBar(
            selectedStatus = StatusFilter.ALL,
            onStatusSelected = {},
            onAddClick = {},
            onPriorityClick = {},
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomFilterBarDarkPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        BottomFilterBar(
            selectedStatus = StatusFilter.ALL,
            onStatusSelected = {},
            onAddClick = {},
            onPriorityClick = {},
            modifier = Modifier
        )
    }
}
