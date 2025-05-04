package com.example.sentience.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.AuthRepository
import com.example.sentience.model.TokenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import android.util.Log

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    companion object {
        private const val TAG = "AuthViewModel"
    }

    private val _registerResult = MutableStateFlow<Response<TokenResponse>?>(null)
    val registerResult: StateFlow<Response<TokenResponse>?> = _registerResult

    private val _registerError = MutableStateFlow<String?>(null)
    val registerError: StateFlow<String?> = _registerError

    private val _isRegistering = MutableStateFlow(false)
    val isRegistering: StateFlow<Boolean> = _isRegistering

    fun register(username: String, password: String, profilePic: String) {
        viewModelScope.launch {
            _isRegistering.value = true
            _registerError.value = null
            
            try {
                val response = repository.register(username, password, profilePic)
                _registerResult.value = response
                
                if (!response.isSuccessful) {
                    _registerError.value = "Registration failed: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e(TAG, "Registration error", e)
                _registerError.value = "Registration failed: ${e.message}"
            } finally {
                _isRegistering.value = false
            }
        }
    }

    private val _loginResult = MutableStateFlow<Response<TokenResponse>?>(null)
    val loginResult: StateFlow<Response<TokenResponse>?> = _loginResult

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn: StateFlow<Boolean> = _isLoggingIn

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoggingIn.value = true
            _loginError.value = null
            
            try {
                val response = repository.login(username, password)
                _loginResult.value = response
                
                if (!response.isSuccessful) {
                    _loginError.value = "Login failed: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e(TAG, "Login error", e)
                _loginError.value = "Login failed: ${e.message}"
            } finally {
                _isLoggingIn.value = false
            }
        }
    }

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> = _usernameError

    private val _isFetchingUsername = MutableStateFlow(false)
    val isFetchingUsername: StateFlow<Boolean> = _isFetchingUsername

    fun fetchUsername() {
        viewModelScope.launch {
            _isFetchingUsername.value = true
            _usernameError.value = null
            
            try {
                val response = repository.getUserProfile()
                if (response.isSuccessful) {
                    val username = response.body()?.user?.user_id ?: "Unknown"
                    _username.value = username
                    Log.d(TAG, "Successfully fetched username: $username")
                } else {
                    _usernameError.value = "Failed to fetch username: ${response.code()}"
                    Log.e(TAG, "Failed to fetch username: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching username", e)
                _usernameError.value = "Error fetching username: ${e.message}"
            } finally {
                _isFetchingUsername.value = false
            }
        }
    }
}

