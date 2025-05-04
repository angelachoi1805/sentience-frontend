package com.example.sentience.data

import com.example.sentience.model.AIRequest
import com.example.sentience.model.AIResponse
import com.example.sentience.network.SentienceApi
import com.example.sentience.util.TokenManager
import retrofit2.Response

class ChatRepository(private val api: SentienceApi, private val tokenManager: TokenManager) {
    suspend fun askAI(prompt: String): Response<AIResponse> {
        return api.askAI(AIRequest(prompt))
    }
}