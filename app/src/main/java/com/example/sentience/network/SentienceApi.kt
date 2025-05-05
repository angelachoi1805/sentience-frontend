package com.example.sentience.network

import com.example.sentience.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import android.util.Log
import retrofit2.http.Path


data class ArticlesResponse(
    val articles: List<ArticleItem>
)

data class TestsResponse(
    val tests: List<TestItem>
)
data class TestDetailsResponse(
    val test: Test,
    val questions: List<QuestionWithOptions>
)

data class Test(
    val id: Int,
    val title: String,
    val description: String
)

data class QuestionWithOptions(
    val question: Question,
    val options: List<Option>
)

data class Question(
    val id: Int,
    val test_id: Int,
    val text: String,
    val order_number: Int
)

data class Option(
    val id: Int,
    val question_id: Int,
    val option_text: String
)

data class EstimateTestRequest(
    val answers: Map<Int, Int>  // Карта вопросов и выбранных ответов
)
data class EstimateTestResponse(
    val message: String,
    val result: TestResult
)

data class TestResult(
    val test_id: Int,
    val user_id: Int,
    val score: Int,
    val created_at: String
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

    @POST("/ask-ai")
    suspend fun askAI(@Body request: AIRequest): Response<AIResponse>

    @GET("/articles")
    suspend fun getArticles(): Response<ArticlesResponse>

    @GET("/tests")
    suspend fun getTests(): Response<TestsResponse>

    @GET("/tests_results")
    suspend fun getTestResults(): Response<UserTestResponse>

    @GET ("/tests/{id}")
    suspend fun getTestDetails(@Path("id") id: Int): Response<TestDetailsResponse>
    @POST("/estimate-test/{test_id}")
    suspend fun estimateTest(
        @Path("test_id") test_id: Int,
        @Body request: EstimateTestRequest
    ): Response<EstimateTestResponse>
}
