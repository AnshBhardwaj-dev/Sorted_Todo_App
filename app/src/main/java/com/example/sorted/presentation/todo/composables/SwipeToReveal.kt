package com.example.sorted.presentation.todo.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
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
            .clip(RoundedCornerShape(12.dp))
    ) {

        // BACKGROUND ACTIONS
        Row(
            modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ActionButton(
                color = Color(0xFF4CAF50),
                icon = Icons.Default.CheckCircle
            ) {
                scope.launch {
                    offsetX.animateTo(0f, tween(300))
                    onDone()
                }
            }

            ActionButton(
                color = Color(0xFF2196F3),
                icon = Icons.Default.Edit
            ) {
                scope.launch {
                    offsetX.animateTo(0f, tween(300))
                    onEdit()
                }
            }

            ActionButton(
                color = Color(0xFFF44336),
                icon = Icons.Default.Delete
            ) {
                scope.launch {
                    offsetX.animateTo(0f, tween(300))
                    onDelete()
                }
            }
        }

        // FOREGROUND
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val newOffset =
                            (offsetX.value + delta).coerceIn(-maxSwipe, 0f)
                        scope.launch {
                            offsetX.snapTo(newOffset)
                        }
                    },
                    onDragStopped = {
                        scope.launch {
                            if (offsetX.value < -maxSwipe / 2) {
                                offsetX.animateTo(-maxSwipe, tween(300))
                            } else {
                                offsetX.animateTo(0f, tween(300))
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
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .fillMaxHeight()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}