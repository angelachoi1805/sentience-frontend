package com.example.sentience

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sentience.data.*
import com.example.sentience.network.RetrofitInstance
import com.example.sentience.ui.component.BottomNavItem
import com.example.sentience.ui.screen.*
import com.example.sentience.ui.theme.SentienceTheme
import com.example.sentience.util.TokenManager
import com.example.sentience.viewmodel.*
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var authViewModel: AuthViewModel
    private lateinit var articlesViewModel: ArticlesViewModel
    private lateinit var testsViewModel: TestsViewModel
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize dependencies
        val tokenManager = TokenManager(applicationContext)
        val api = RetrofitInstance.create(tokenManager)
        Log.d(TAG, "Token status: ${if (tokenManager.getToken() != null) "Present" else "Not present"}")

        // Initialize repositories
        val authRepository = AuthRepository(api, tokenManager)
        val chatRepository = ChatRepository(api, tokenManager)
        val articleRepository = ArticleRepository(api, tokenManager)
        val testRepository = TestRepository(api, tokenManager)

        // Initialize ViewModels
        authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(authRepository)
        )[AuthViewModel::class.java]

        articlesViewModel = ViewModelProvider(
            this,
            ArticlesViewModelFactory(articleRepository)
        )[ArticlesViewModel::class.java]

        testsViewModel = ViewModelProvider(
            this,
            TestsViewModelFactory(testRepository)
        )[TestsViewModel::class.java]

        chatViewModel = ChatViewModel(chatRepository)
        val profileViewModel = ProfileViewModel(ProfileRepository(api))
        // Check authentication state
        val isAuthenticated = tokenManager.getToken() != null

        setContent {
            SentienceTheme {
                val navController = rememberNavController()
                val username by authViewModel.username.collectAsState()
                val articles by articlesViewModel.articles.collectAsState()
                val tests by testsViewModel.tests.collectAsState()
                Log.d("TESTS ", "$tests")
                val articlesError by articlesViewModel.error.collectAsState()
                val testsError by testsViewModel.error.collectAsState()
                val usernameError by authViewModel.usernameError.collectAsState()
                val isFetchingUsername by authViewModel.isFetchingUsername.collectAsState()

                // Monitor token changes
                LaunchedEffect(tokenManager.getToken()) {
                    if (tokenManager.getToken() == null && navController.currentDestination?.route != "login") {
                        Log.d(TAG, "Token cleared, redirecting to login")
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }

                // Handle authentication errors
                LaunchedEffect(usernameError) {
                    usernameError?.let { error ->
                        Log.e(TAG, "Username error: $error")
                        if (error.contains("401") || error.contains("Unauthorized")) {
                            // Token is invalid, clear it and force login
                            tokenManager.clearToken()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                }

                // Fetch username if authenticated
                if (isAuthenticated) {
                    LaunchedEffect(Unit) {
                        authViewModel.fetchUsername()
                    }
                }

                // Show errors if any
                LaunchedEffect(articlesError) {
                    articlesError?.let { error ->
                        Log.e(TAG, "Articles error: $error")
                    }
                }

                LaunchedEffect(testsError) {
                    testsError?.let { error ->
                        Log.e(TAG, "Tests error: $error")
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = if (isAuthenticated) "home" else "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            viewModel = authViewModel,
                            onNavigateToRegister = { navController.navigate("register") },
                            onLoginSuccess = {
                                navController.navigate("home")

                            }
                        )
                    }

                    composable("register") {
                        RegisterScreen(
                            viewModel = authViewModel,
                            onRegisterSuccess = { navController.navigate("home") },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable("chat") {
                        ChatScreen(
                            username = username ?: "User",
                            viewModel = chatViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable("home") {
                        val moodHistory = remember { mutableStateMapOf<LocalDate, Int>() }
                        val mood = remember { mutableStateOf(2f) }
                        val moodDescriptions = listOf(
                            "Woke up on the wrong side of the bed",
                            "Meh, could be worse",
                            "Just another day",
                            "Feeling pretty good",
                            "Over the moons!"
                        )
                        val selectedItem = remember { mutableStateOf(BottomNavItem.Home) }

                        HomeScreen(
                            username = username ?: "User",
                            articles = articles,
                            tests = tests,
                            moodHistory = moodHistory,
                            onArticleClick = { article -> 
                                navController.navigate("article/${article.id}") 
                            },
                            onTestClick = { test -> 
                                navController.navigate("test/${test.id}") 
                            },
                            onMoodChange = { newMood -> 
                                mood.value = newMood 
                            },
                            onSubmitMood = { 
                                moodHistory[LocalDate.now()] = mood.value.toInt() 
                            },
                            mood = mood.value,
                            moodDescription = moodDescriptions[mood.value.toInt()],
                            selectedItem = selectedItem.value,
                            testsViewModel = testsViewModel,
                            articlesViewModel = articlesViewModel,
                            onItemSelected = { newItem -> 
                                selectedItem.value = newItem
                                when (newItem) {
                                    BottomNavItem.Chat -> navController.navigate("chat")
                                    BottomNavItem.Home -> navController.navigate("home")
                                    BottomNavItem.Profile -> navController.navigate("profile")
                                }
                            }
                        )
                    }
                    composable("profile") {
                        ProfileScreen(
                            username = username ?: "User",
                            onBack = {
                                navController.navigate("home")
                            },
                            profileViewModel= profileViewModel

                        )
                    }
                    composable("articles") {
                        AllArticlesScreen(
                            articles = articles,
                            onArticleClick = { article -> 
                                navController.navigate("article/${article.id}") 
                            },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    composable("test/{testId}") {
                        backStackEntry->
                        val testId = backStackEntry.arguments?.getString("testId")?.toIntOrNull()
                        val test = tests.find { it.id == testId }
                            if (test != null){
                                TestScreen(
                                    test = test,
                                    testsViewModel = testsViewModel,
                                    onNavigateBack = { navController.popBackStack() }
                                )
                            }
                    }

                    composable("article/{articleId}") { backStackEntry ->
                        val articleId = backStackEntry.arguments?.getString("articleId")?.toIntOrNull()
                        val article = articles.find { it.id == articleId }
                        if (article != null) {
                            ArticleScreen(
                                article = article,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}