package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    val cardColor = when (title) {
        "PENDING" -> MaterialTheme.colorScheme.tertiaryContainer  // #BCEBEF - Light teal
        "HIGH", "CRITICAL" -> MaterialTheme.colorScheme.errorContainer  // #FFDAD6 - Light red
        "FINISHED" -> MaterialTheme.colorScheme.primaryContainer  // #BEF0B2 - Light green
        "EFFICIENCY" -> MaterialTheme.colorScheme.secondaryContainer  // #D6E8CE - Light gray-green
        else -> MaterialTheme.colorScheme.surfaceVariant  // #DEE4D8 - Default light gray
    }

    // Determine text color for the value
    val valueColor = when (title) {
        "PENDING" -> MaterialTheme.colorScheme.onTertiaryContainer  // #1E4D51 - Dark teal
        "HIGH", "CRITICAL" -> MaterialTheme.colorScheme.onErrorContainer  // #93000A - Dark red
        "FINISHED" -> MaterialTheme.colorScheme.onPrimaryContainer  // #265022 - Dark green
        "EFFICIENCY" -> MaterialTheme.colorScheme.onSecondaryContainer  // #3C4B38 - Dark gray-green
        else -> MaterialTheme.colorScheme.onSurface  // #191D17 - Dark gray
    }

    Card(
        modifier = modifier
            .height(100.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = MaterialTheme.colorScheme.outlineVariant,
                spotColor = MaterialTheme.colorScheme.outlineVariant
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = valueColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

fun getTodayDate(): String {
    val formatter = SimpleDateFormat(
        "EEEE, MMMM dd",
        Locale.getDefault()
    )
    return formatter.format(Date())
}