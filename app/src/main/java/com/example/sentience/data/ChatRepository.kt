package com.example.sentience.data

import com.example.sentience.model.AIRequest
import com.example.sentience.model.AIResponse
import com.example.sentience.network.SentienceApi
import retrofit2.Response

class ChatRepository(private val api: SentienceApi) {

    suspend fun askAI(token: String, prompt: String): Response<AIResponse> {
        val response = api.askAI("Bearer $token", AIRequest(prompt))
        return response
    }
}