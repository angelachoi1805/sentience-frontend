package com.example.sentience

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sentience.data.AuthRepository
import com.example.sentience.data.ChatRepository
import com.example.sentience.ui.screen.RegisterScreen
import com.example.sentience.viewmodel.AuthViewModel
import com.example.sentience.viewmodel.AuthViewModelFactory
import com.example.sentience.network.RetrofitInstance
import com.example.sentience.ui.screen.*
import com.example.sentience.ui.theme.SentienceTheme
import com.example.sentience.util.TokenManager
import com.example.sentience.viewmodel.ChatViewModel

class MainActivity : ComponentActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var chatViewModel: ChatViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenManager = TokenManager(applicationContext)
        val api = RetrofitInstance.create(tokenManager)
        Log.d("MyApp", "Токен: ${tokenManager.getToken()}")

        val repository = AuthRepository(api, tokenManager)
        val chatRepository = ChatRepository(api, tokenManager)
        val viewModel = AuthViewModel(repository)
        val factory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        chatViewModel = ChatViewModel(chatRepository)
        val isAuth = tokenManager.getToken() != null
        
        setContent {
            SentienceTheme {
                val navController = rememberNavController()
                val username by authViewModel.fetchUserNameResult.observeAsState()
                if (isAuth) {
                    Log.d("REQUEST", "fetching ${isAuth}")
                    LaunchedEffect(Unit) {
                        authViewModel.fetchUsername()
                    }
                }
                NavHost(navController = navController, startDestination= if(isAuth) "chat" else "login") {
                    composable("login") {
                        LoginScreen(
                            viewModel = authViewModel,
                            onNavigateToRegister = { navController.navigate("register") },
                            onLoginSuccess = { navController.navigate("chat") }
                        )
                    }
                    composable("register") {
                        RegisterScreen(
                            viewModel = authViewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    composable("chat"){
                        Log.d("USERNAME", "AD ${username}")
                        ChatScreen(username = username ?: "User", chatViewModel)
                    }

//                    composable("home"){
//                        HomeScreen(
//                            viewModel = authViewModel,
//                            username = "User",
//                            onNavigateBack = {navController.popBackStack() }
//                        )
//                    }
                }
            }
        }



    }
}
