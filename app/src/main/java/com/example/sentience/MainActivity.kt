package com.example.sentience

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sentience.data.AuthRepository
import com.example.sentience.ui.screen.RegisterScreen
import com.example.sentience.viewmodel.AuthViewModel
import com.example.sentience.viewmodel.AuthViewModelFactory
import com.example.sentience.network.RetrofitInstance
import com.example.sentience.ui.screen.*
import com.example.sentience.ui.theme.SentienceTheme
import com.example.sentience.util.TokenManager

class MainActivity : ComponentActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenManager = TokenManager(applicationContext)
        val api = RetrofitInstance.create(tokenManager)
        val repository = AuthRepository(api, tokenManager)
        val viewModel = AuthViewModel(repository)
        val factory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        setContent {
            SentienceTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            viewModel = authViewModel,
                            onNavigateToRegister = { navController.navigate("register") }
                        )
                    }
                    composable("register") {
                        RegisterScreen(
                            viewModel = authViewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
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
