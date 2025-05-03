package com.example.sentience.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sentience.model.TestItem

@Composable
fun TestCardRow(
    tests: List<TestItem>,
    onSeeAllClick: () -> Unit,
    onStartTestClick: (TestItem) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Take a Self-Test",
                style = MaterialTheme.typography.titleMedium
            )
            TextButton(onClick = onSeeAllClick) {
                Text("See All")
            }
        }

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            items(tests) { test ->
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(end = 12.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = test.title,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${test.minutes} min",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = test.description.take(100) + "...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onStartTestClick(test) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Start Test")
                        }
                    }
                }
            }
        }
    }
}
