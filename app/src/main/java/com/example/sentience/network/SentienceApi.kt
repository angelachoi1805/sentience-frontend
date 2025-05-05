package com.example.sentience.network

import com.example.sentience.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import android.util.Log


data class ArticlesResponse(
    val articles: List<ArticleItem>
)

data class TestsResponse(
    val tests: List<TestItem>
)


interface SentienceApi {
    companion object {
        private const val TAG = "SentienceApi"
    }

    // Login endpoint
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<TokenResponse>

    // Register endpoint
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<TokenResponse>

    @GET("/me")
    suspend fun getProfile(): Response<UserProfile>

    // Ask AI endpoint
    @POST("/ask-ai")
    suspend fun askAI(@Body request: AIRequest): Response<AIResponse>

    @GET("/articles")
    suspend fun getArticles(): Response<ArticlesResponse>

    @GET("/tests")
    suspend fun getTests(): Response<TestsResponse>
}
