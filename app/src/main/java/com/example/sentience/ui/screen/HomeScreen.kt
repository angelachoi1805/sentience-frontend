//package com.example.sentience.ui.screen
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import com.example.sentience.util.TokenManager
//import com.example.sentience.viewmodel.AuthViewModel
//import com.example.sentience.ui.component.*
//import java.time.LocalDate
//import java.time.format.TextStyle
//import java.util.*
//
//@Composable
//fun HomeScreen(
//    viewModel: AuthViewModel,
//    username: String,
////    quotesOfDay: Pair<String, String>, // Pair(content, author)
////    articles: List<ArticlePreview>,
////    tests: List<TestPreview>,
////    onStartTest: (TestPreview) -> Unit,
////    onSeeAllArticles: () -> Unit,
////    onSeeAllTests: () -> Unit,
//      onNavigateBack: () -> Unit = {}
////    onBottomNavSelect: (HomeTab) -> Unit
//) {
//    val context = LocalContext.current
//    val tokenManager = TokenManager(context)
//
//    // Bottom Navigation
//    BottomAppBar(
//        containerColor = MaterialTheme.colorScheme.background
//    ) {
////        BottomNavItem("Chat", HomeTab.Chat, onBottomNavSelect)
////        BottomNavItem("Home", HomeTab.Home, onBottomNavSelect)
////        BottomNavItem("Profile", HomeTab.Profile, onBottomNavSelect)
//    }
//
//    //LaunchedEffect(Unit) {
//    //    viewModel.fetchUsername()
//    //}
//
//    @Composable
//    fun HomeScreen(viewModel: AuthViewModel) {
//        val mood = remember { mutableFloatStateOf(2f) }
//        val moodDescriptions = listOf(...)
//
//        val username = viewModel.username.collectAsState()
//        val today = LocalDate.now()
//        val pastWeek = (0..6).map { today.minusDays((6 - it).toLong()) }
//        val weekdays = pastWeek.map { it.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }
//
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "Hi ${username.value},",
//                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//            )
//
//            MoodSlider(
//                mood = mood.floatValue,
//                onMoodChange = { mood.floatValue = it },
//                onSubmitMood = {
//                    viewModel.saveMood(today, mood.floatValue.toInt())
//                },
//                moodDescription = moodDescriptions[mood.floatValue.toInt()]
//            )
//
//            MoodCalendar(
//                moodHistory = viewModel.moodHistory,
//                pastWeek = pastWeek,
//                weekdays = weekdays
//            )
//        }
//    }
//
//
//    Spacer(modifier = Modifier.height(24.dp))
//
//        // Quote of the Day
//        Text(
//            text = "Quote of the Day:",
//            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
//        )
//        Card(
//            shape = RoundedCornerShape(8.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text("Quote of the Day", style = MaterialTheme.typography.bodyLarge)
//                Spacer(modifier = Modifier.height(4.dp))
//                Text("Meow", style = MaterialTheme.typography.bodySmall)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Resources & Readings
//        SectionWithSeeAll(
//            title = "Resources & Readings",
//            onSeeAll = TODO()
////            onSeeAll = onSeeAllArticles
//        ) {
//            LazyRow {
////                items(articles) { article ->
////                    ArticleCardPreview(article)
////                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Take a Self-Test
//        SectionWithSeeAll(
//            title = "Take a Self-Test",
//            onSeeAll = TODO(),
////            onSeeAll = onSeeAllTests
//        ) {
//            LazyRow {
////                items(tests) { test ->
////                    TestCardPreview(test, onStartTest)
////                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(56.dp)) // space for bottom nav
//    }
//}
//
//@Composable
//private fun ArticleCardPreview(article: ArticlePreview) {
//    Card(
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//            .width(200.dp)
//            .padding(end = 8.dp)
//    ) {
//        Column(modifier = Modifier.padding(12.dp)) {
//            Text(article.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text("${article.readTime} min read", style = MaterialTheme.typography.bodySmall)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(article.description.take(100) + "...", style = MaterialTheme.typography.bodyMedium)
//        }
//    }
//}
//
//@Composable
//private fun TestCardPreview(test: TestPreview, onStartTest: (TestPreview) -> Unit) {
//    Card(
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//            .width(200.dp)
//            .padding(end = 8.dp)
//            .clickable { onStartTest(test) }
//    ) {
//        Column(modifier = Modifier.padding(12.dp)) {
//            Text(test.title, style = MaterialTheme.typography.titleSmall, maxLines = 1)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text("${test.timeMinutes} min", style = MaterialTheme.typography.bodySmall)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(test.description.take(100) + "...", style = MaterialTheme.typography.bodyMedium)
//            Spacer(modifier = Modifier.height(8.dp))
//            Button(onClick = { onStartTest(test) }, modifier = Modifier.fillMaxWidth()) {
//                Text("Start Test")
//            }
//        }
//    }
//}
//
//@Composable
//private fun SectionWithSeeAll(
//    title: String,
//    onSeeAll: () -> Unit,
//    content: @Composable RowScope.() -> Unit
//) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
//        TextButton(onClick = onSeeAll) {
//            Text("See all")
//        }
//    }
//    //content()
//}
//
//@Composable
//private fun BottomNavItem(
//    label: String,
//    tab: HomeTab,
//    onSelect: (HomeTab) -> Unit
//) {
//    Text(
//        text = label,
//        modifier = Modifier
//           // .weight(1f)
//            .clickable { onSelect(tab) }
//            .padding(12.dp),
//        textAlign = TextAlign.Center
//    )
//}
//
//// Data classes and enums for preview
//
//data class ArticlePreview(val title: String, val readTime: Int, val description: String)
//data class TestPreview(val title: String, val timeMinutes: Int, val description: String)
//enum class HomeTab { Chat, Home, Profile }
