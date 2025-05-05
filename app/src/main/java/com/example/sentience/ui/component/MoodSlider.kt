package com.example.sentience.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodSlider(
    mood: Float,
    onMoodChange: (Float) -> Unit,
    onSubmitMood: () -> Unit,
    moodDescription: String
) {
    val moodColors = listOf(
        Color(0xFFE57373), // Sad
        Color(0xFFFFB74D), // Neutral
        Color(0xFFFFD54F), // Slightly Happy
        Color(0xFF81C784), // Happy
        Color(0xFF4CAF50)  // Very Happy
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "How are you feeling today?",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("😢", "😐", "😊", "😄", "🤩").forEachIndexed { index, emoji ->
                    val isSelected = index == mood.toInt()
                    val scale by animateFloatAsState(
                        targetValue = if (isSelected) 1.3f else 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) moodColors[index].copy(alpha = 0.1f)
                                else MaterialTheme.colorScheme.surface
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = emoji,
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier.scale(scale)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Slider(
                value = mood,
                onValueChange = onMoodChange,
                valueRange = 0f..4f,
                steps = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                colors = SliderDefaults.colors(
                    thumbColor = moodColors[mood.toInt()],
                    activeTrackColor = moodColors[mood.toInt()],
                    inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(moodColors[mood.toInt()])
                            .padding(4.dp)
                    )
                }
            )

            Text(
                text = moodDescription,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            Button(
                onClick = onSubmitMood,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = moodColors[mood.toInt()],
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.large,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    "Submit Mood",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}