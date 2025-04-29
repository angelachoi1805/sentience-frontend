package com.example.sentience.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.ChatRepository
import com.example.sentience.model.AIRequest
import com.example.sentience.model.AIResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class ChatViewModel(application: Application, private val repository: ChatRepository) : AndroidViewModel(application) {

    fun askAI(token: String, prompt: String) = liveData {
        try {
            val response: Response<AIResponse> = repository.askAI(token, prompt)

            if (response.isSuccessful) {
                emit(response.body()) // Emit AI response if successful
            } else {
                emit(null) // Emit null if error occurs
            }
        } catch (e: Exception) {
            emit(null) // Emit null if network error occurs
        }
    }
}
