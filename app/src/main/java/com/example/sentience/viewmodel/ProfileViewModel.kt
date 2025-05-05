package com.example.sentience.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.ChatRepository
import com.example.sentience.data.ProfileRepository
import com.example.sentience.model.AIRequest
import com.example.sentience.model.AIResponse
import com.example.sentience.model.UserTestResultItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val _sendMessageResult = MutableStateFlow<List<UserTestResultItem>>(emptyList())
    val results: StateFlow<List<UserTestResultItem>> = _sendMessageResult

    fun getResults() {
        viewModelScope.launch {
            val response = repository.getTestResults()
            _sendMessageResult.value = response.body()?.results!!

        }
    }
}
