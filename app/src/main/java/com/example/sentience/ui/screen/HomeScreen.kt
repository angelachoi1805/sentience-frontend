package com.example.sentience.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import com.example.sentience.model.ArticleItem
import com.example.sentience.model.TestItem
import com.example.sentience.ui.component.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    username: String,
    articles: List<ArticleItem>,
    tests: List<TestItem>,
    moodHistory: Map<LocalDate, Int>,
    onArticleClick: (ArticleItem) -> Unit,
    onTestClick: (TestItem) -> Unit,
    onMoodChange: (Float) -> Unit,
    onSubmitMood: () -> Unit,
    mood: Float,
    moodDescription: String,
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    // Mood History for past week
    val today = LocalDate.now()
    val pastWeek = (0..6).map { today.minusDays((6 - it).toLong()) }
    val weekdays = pastWeek.map { it.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = onItemSelected
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Greeting Text
            GreetingSection(username)

            Spacer(modifier = Modifier.height(16.dp))

            // Mood Slider
            MoodSlider(
                mood = mood,
                onMoodChange = onMoodChange,
                onSubmitMood = onSubmitMood,
                moodDescription = moodDescription
            )

            // Mood Calendar
            MoodCalendar(
                moodHistory = moodHistory,
                pastWeek = pastWeek,
                weekdays = weekdays
            )

            // Quote of the Day
            QuoteBox(
//            quote = quotesOfDay.first,
//            author = quotesOfDay.second
            )

            // Articles Row
            ArticleCardRow(
                articles = articles,
                onSeeAllClick = { },
                onArticleClick = onArticleClick
            )

            // Tests Row
            TestCardRow(
                tests = tests,
                onSeeAllClick = { },
                onStartTestClick = onTestClick
            )
        }
    }
}

@Composable
private fun GreetingSection(username: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Hi $username,",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "How are you feeling today?",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
