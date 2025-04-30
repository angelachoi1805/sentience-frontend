package com.example.sentience.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@Composable
fun MoodSlider(
    mood: Float,
    onMoodChange: (Float) -> Unit,
    onSubmitMood: () -> Unit,
    moodDescription: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "How are you feeling today?",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Slider(
            value = mood,
            onValueChange = onMoodChange,
            valueRange = 0f..4f,
            steps = 4,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = moodDescription,
            style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Button(onClick = onSubmitMood) {
            Text("Submit Mood")
        }
    }
}