package com.example.sentience.network

import com.example.sentience.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET

interface SentienceApi {

    // Login endpoint
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<TokenResponse>

    // Register endpoint
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<TokenResponse>

    @GET("profile")
    suspend fun getProfile(): UserProfile

    // Ask AI endpoint
    @POST("/ask-ai")
    suspend fun askAI(
        @Header("Authorization") token: String,
        @Body request: AIRequest
    ): Response<AIResponse>
}
