package com.example.sentience.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    onArticleClick: (ArticleItem) -> Unit,
    onSeeAllArticles: () -> Unit,
    onStartTestClick: (TestItem) -> Unit,
    onSeeAllTests: () -> Unit,
    onBottomNavSelect: (BottomNavItem) -> Unit
) {
    // Greeting and Mood State
    var mood by remember { mutableStateOf(2f) }
    val moodDescriptions = listOf(
        "Woke up on the wrong side of the bed",
        "Meh, could be worse",
        "Just another day",
        "Feeling pretty good",
        "Over the moons!"
    )

    // Mood History for past week
    var moodHistory by remember { mutableStateOf(mutableMapOf<LocalDate, Int>()) }
    val today = LocalDate.now()
    val pastWeek = (0..6).map { today.minusDays((6 - it).toLong()) }
    val weekdays = pastWeek.map { it.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Greeting Text
        GreetingSection(username)

        Spacer(modifier = Modifier.height(16.dp))

        // Mood Slider
        MoodSlider(
            mood = mood,
            onMoodChange = { mood = it },
            onSubmitMood = { moodHistory[today] = mood.toInt() },
            moodDescription = moodDescriptions[mood.toInt()]
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
            onSeeAllClick = onSeeAllArticles,
            onArticleClick = onArticleClick
        )

        // Tests Row
        TestCardRow(
            tests = tests,
            onSeeAllClick = onSeeAllTests,
            onStartTestClick = onStartTestClick
        )
    }

    // Bottom Navigation
    BottomNavigationBar(
        selectedItem = BottomNavItem.Home,
        onItemSelected = onBottomNavSelect
    )
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
