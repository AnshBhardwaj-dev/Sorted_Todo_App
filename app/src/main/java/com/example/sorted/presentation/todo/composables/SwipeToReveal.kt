package com.example.sorted.presentation.todo.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeToReveal(
    title: String,
    description: String? = null,
    dueDate: Long,
    priority: String,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onDone: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val offsetX = remember { Animatable(0f) }
    val actionWidthPx = with(density) { 80.dp.toPx() }
    val maxSwipe = actionWidthPx * 3

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp
            )
    ) {

        // BACKGROUND ACTIONS (always behind)
        Row(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHighest),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // DONE Button
            ActionButton(
                color    = MaterialTheme.colorScheme.primaryContainer,
                icon = Icons.Default.CheckCircle,
                MaterialTheme.colorScheme.onPrimaryContainer,
                onClick = {
                    scope.launch {
                        offsetX.animateTo(
                            0f,
                            tween(300)
                        )
                        onDone()
                        onDelete()
                    }
                }
            )

            // EDIT Button
            ActionButton(
                color    = MaterialTheme.colorScheme.tertiaryContainer,
                iconTint = MaterialTheme.colorScheme.onTertiaryContainer,
                icon = Icons.Default.Edit,
                onClick = {
                    scope.launch {
                        offsetX.animateTo(
                            0f,
                            tween(300)
                        )
                        onEdit()
                    }
                }
            )

            // DELETE Button
            ActionButton(
                color = MaterialTheme.colorScheme.error,
                icon = Icons.Default.Delete,
                iconTint = MaterialTheme.colorScheme.onError,
                onClick = {
                    scope.launch {
                        offsetX.animateTo(
                            0f,
                            tween(300)
                        )
                        onDelete()
                    }
                }
            )
        }

        // FOREGROUND - Swipeable Todo Item (fully opaque)
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        offsetX.value.roundToInt(),
                        0
                    )
                }
                .fillMaxWidth()
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val newOffset = (offsetX.value + delta).coerceIn(
                            -maxSwipe,
                            0f
                        )
                        scope.launch {
                            offsetX.snapTo(newOffset)
                        }
                    },
                    onDragStopped = {
                        scope.launch {
                            if (offsetX.value < -maxSwipe / 2) {
                                offsetX.animateTo(
                                    -maxSwipe,
                                    tween(300)
                                )
                            } else {
                                offsetX.animateTo(
                                    0f,
                                    tween(300)
                                )
                            }
                        }
                    }
                )
        ) {
            TodoItem(
                title = title,
                description = description,
                dueDate = dueDate,
                priority = priority
            )
        }
    }
}

@Composable
private fun ActionButton(
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .fillMaxHeight()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(
                        12
                            .dp
                    )
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}