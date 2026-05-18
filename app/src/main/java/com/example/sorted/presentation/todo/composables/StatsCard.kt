package com.example.sorted.presentation.todo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sorted.ui.theme.displayFontFamily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private data class StatTone(
    val gradientLight: List<Color>,
    val gradientDark: List<Color>,
    val textLight: Color,
    val textDark: Color,
    val shadow: Color
)

private val AmberTone = StatTone(
    gradientLight = listOf(Color(0xFFFBD9A6), Color(0xFFF8C98A), Color(0xFFF2B879)),
    gradientDark  = listOf(Color(0xFF6B4220), Color(0xFF553418), Color(0xFF3F2611)),
    textLight = Color(0xFF6B3A1A), textDark = Color(0xFFF8DCB0),
    shadow = Color(0xFFD9833A)
)
private val RoseTone = StatTone(
    gradientLight = listOf(Color(0xFFF6D2CE), Color(0xFFEFB8B5), Color(0xFFE69E9C)),
    gradientDark  = listOf(Color(0xFF8B2A30), Color(0xFF6B1F23), Color(0xFF4A1418)),
    textLight = Color(0xFF6B1F23), textDark = Color(0xFFF6D2CE),
    shadow = Color(0xFFB0373C)
)
private val BlushTone = StatTone(
    gradientLight = listOf(Color(0xFFFBE0DC), Color(0xFFF4CAC6), Color(0xFFEDB6B2)),
    gradientDark  = listOf(Color(0xFF5A2A30), Color(0xFF451E24), Color(0xFF2F1418)),
    textLight = Color(0xFF6B2A2D), textDark = Color(0xFFF4D2D0),
    shadow = Color(0xFFC05A5A)
)
private val MauveTone = StatTone(
    gradientLight = listOf(Color(0xFFF4D2D4), Color(0xFFEBB9BD), Color(0xFFDFA0A6)),
    gradientDark  = listOf(Color(0xFF4A3236), Color(0xFF3A2528), Color(0xFF2A1A1D)),
    textLight = Color(0xFF5C1C24), textDark = Color(0xFFE8C8CC),
    shadow = Color(0xFF963746)
)


@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val tone = when (title) {
        "PENDING"    -> AmberTone
        "HIGH", "CRITICAL" -> RoseTone
        "FINISHED"   -> BlushTone
        "EFFICIENCY" -> MauveTone
        else         -> BlushTone
    }
    val gradient = if (isDark) tone.gradientDark else tone.gradientLight
    val textColor = if (isDark) tone.textDark else tone.textLight

    Box(
        modifier = modifier
            .height(110.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = tone.shadow.copy(alpha = .45f),
                spotColor = tone.shadow.copy(alpha = .45f)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.linearGradient(gradient))
    ) {

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = textColor.copy(alpha = .7f),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.5.sp,
                maxLines = 1
            )
            Text(
                text = value,
                fontFamily = displayFontFamily,    // Fraunces
                color = textColor,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 36.sp
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