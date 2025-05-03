package com.example.sentience.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.AuthRepository
import com.example.sentience.model.TokenResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<Response<TokenResponse>>()
    val registerResult: LiveData<Response<TokenResponse>> get() = _registerResult
    fun register(username: String, password: String, profilePic: String) {
        viewModelScope.launch {
            val response = repository.register(username, password, profilePic)
            _registerResult.value = response
        }
    }

    private val _loginResult = MutableLiveData<Response<TokenResponse>>()
    val loginResult: LiveData<Response<TokenResponse>> get() = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val response = repository.login(username, password)
            _loginResult.value = response
        }
    }

    val fetchUserNameResult: MutableLiveData<String> = MutableLiveData()

    fun fetchUsername() {
        viewModelScope.launch {
            try {
                val response = repository.getUserProfile()
                if (response.isSuccessful) {
                    val username = response.body()?.user?.user_id ?: "Unknown"
                    fetchUserNameResult.value = username
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


}

