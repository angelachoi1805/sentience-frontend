package com.example.sentience.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.ChatRepository
import com.example.sentience.model.AIRequest
import com.example.sentience.model.AIResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {
    private val _sendMessageResult = MutableStateFlow<String?>(null)
    val sendMessageResult: StateFlow<String?> = _sendMessageResult

    fun askAI(text: String) {
        viewModelScope.launch {
            val response = repository.askAI(text)
            _sendMessageResult.value = response.body()?.response
        }
    }
}
