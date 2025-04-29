package com.example.sentience.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sentience.util.TokenManager
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Composable
fun HomeScreen(
    username: String,
    quotesOfDay: Pair<String, String>, // Pair(content, author)
    articles: List<ArticlePreview>,
    tests: List<TestPreview>,
    onStartTest: (TestPreview) -> Unit,
    onSeeAllArticles: () -> Unit,
    onSeeAllTests: () -> Unit,
    onBottomNavSelect: (HomeTab) -> Unit
) {
    val context = LocalContext.current
    val tokenManager = TokenManager(context)
    var mood by remember { mutableStateOf(2f) }
    val moodDescriptions = listOf(
        "Woke up on the wrong side of the bed",
        "Meh, could be worse",
        "Just another day",
        "Feeling pretty good",
        "Over the moons!"
    )

    // Simulated tracker: last 7 days moods (nullable if not set)
    var moodHistory by remember { mutableStateOf(mutableMapOf<LocalDate, Int>()) }
    val today = LocalDate.now()
    val pastWeek = (0..6).map { today.minusDays((6 - it).toLong()) }
    val weekdays = pastWeek.map { it.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Greeting
        Text(
            text = "Hi $username,\nhow are you feeling today?",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Mood slider
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Slider(
                value = mood,
                onValueChange = { mood = it },
                valueRange = 0f..4f,
                steps = 4,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = moodDescriptions[mood.toInt()],
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Button(onClick = {
                moodHistory = moodHistory.toMutableMap().apply {
                    put(today, mood.toInt())
                }
            }) {
                Text("Submit Mood")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mood Tracker
        Text(
            text = "Mood Tracker",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            pastWeek.forEachIndexed { index, date ->
                val moodValue = moodHistory[date]
                val circleColor = moodValue?.let { Color(0xFF6200EE).copy(alpha = (0.3f + it * 0.15f)) } ?: Color.LightGray
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(circleColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Row {
            weekdays.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quote of the Day
        Text(
            text = "Quote of the Day:",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("\"${quotesOfDay.first}\"", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text("- ${quotesOfDay.second}", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Resources & Readings
        SectionWithSeeAll(
            title = "Resources & Readings",
            onSeeAll = onSeeAllArticles
        ) {
            LazyRow {
                items(articles) { article ->
                    ArticleCardPreview(article)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Take a Self-Test
        SectionWithSeeAll(
            title = "Take a Self-Test",
            onSeeAll = onSeeAllTests
        ) {
            LazyRow {
                items(tests) { test ->
                    TestCardPreview(test, onStartTest)
                }
            }
        }

        Spacer(modifier = Modifier.height(56.dp)) // space for bottom nav
    }

    // Bottom Navigation
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        BottomNavItem("Chat", HomeTab.Chat, onBottomNavSelect)
        BottomNavItem("Home", HomeTab.Home, onBottomNavSelect)
        BottomNavItem("Profile", HomeTab.Profile, onBottomNavSelect)
    }
}

@Composable
private fun ArticleCardPreview(article: ArticlePreview) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(200.dp)
            .padding(end = 8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(article.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Text("${article.readTime} min read", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(article.description.take(100) + "...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun TestCardPreview(test: TestPreview, onStartTest: (TestPreview) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(200.dp)
            .padding(end = 8.dp)
            .clickable { onStartTest(test) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(test.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Text("${test.timeMinutes} min", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(test.description.take(100) + "...", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onStartTest(test) }, modifier = Modifier.fillMaxWidth()) {
                Text("Start Test")
            }
        }
    }
}

@Composable
private fun SectionWithSeeAll(
    title: String,
    onSeeAll: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        TextButton(onClick = onSeeAll) {
            Text("See all")
        }
    }
    //content()
}

@Composable
private fun BottomNavItem(
    label: String,
    tab: HomeTab,
    onSelect: (HomeTab) -> Unit
) {
    Text(
        text = label,
        modifier = Modifier
           // .weight(1f)
            .clickable { onSelect(tab) }
            .padding(12.dp),
        textAlign = TextAlign.Center
    )
}

// Data classes and enums for preview

data class ArticlePreview(val title: String, val readTime: Int, val description: String)
data class TestPreview(val title: String, val timeMinutes: Int, val description: String)
enum class HomeTab { Chat, Home, Profile }
