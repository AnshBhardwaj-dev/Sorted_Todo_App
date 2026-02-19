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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
    val swipeableState = remember { Animatable(0f) }
    val actionWidth = with(density) { 80.dp.toPx() }
    val maxSwipeAmount = actionWidth * 3 // Three actions

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        // Background actions
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Done action
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxSize()
                    .background(Color(0xFF4CAF50))
                    .clip(
                        RoundedCornerShape(
                            topStart = 10.dp,
                            bottomStart = 10.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            swipeableState.animateTo(
                                0f,
                                tween(300)
                            )
                            onDone()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Done",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Edit action
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxSize()
                    .background(Color(0xFF2196F3)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            swipeableState.animateTo(
                                0f,
                                tween(300)
                            )
                            onEdit()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Delete action
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxSize()
                    .background(Color(0xFFF44336))
                    .clip(
                        RoundedCornerShape(
                            topEnd = 10.dp,
                            bottomEnd = 10.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            swipeableState.animateTo(
                                0f,
                                tween(300)
                            )
                            onDelete()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Foreground item
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        swipeableState.value.roundToInt(),
                        0
                    )
                }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        scope.launch {
                            val newValue = (swipeableState.value + delta)
                                .coerceIn(
                                    -maxSwipeAmount,
                                    0f
                                )
                            swipeableState.snapTo(newValue)
                        }
                    },
                    onDragStopped = {
                        scope.launch {
                            if (swipeableState.value < -maxSwipeAmount / 2) {
                                swipeableState.animateTo(
                                    -maxSwipeAmount,
                                    tween(300)
                                )
                            } else {
                                swipeableState.animateTo(
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