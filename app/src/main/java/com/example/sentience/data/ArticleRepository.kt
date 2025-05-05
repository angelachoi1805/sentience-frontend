package com.example.sentience.data

import android.util.Log
import com.example.sentience.model.*
import com.example.sentience.network.SentienceApi
import com.example.sentience.util.TokenManager
import retrofit2.Response

sealed class ArticleResult {
    data class Success(val articles: List<ArticleItem>) : ArticleResult()
    data class Error(val message: String, val code: Int? = null) : ArticleResult()
}

class ArticleRepository(private val api: SentienceApi, private val tokenManager: TokenManager) {
    companion object {
        private const val TAG = "ArticleRepository"
    }

    suspend fun fetchArticles(): ArticleResult {
        val token = tokenManager.getToken() ?: run {
            Log.e(TAG, "Authentication token not found")
            return ArticleResult.Error("Authentication token not found")
        }
        
        if (token.isBlank()) {
            Log.e(TAG, "Authentication token is blank")
            return ArticleResult.Error("Authentication token is blank")
        }
        
        Log.d(TAG, "Fetching articles with token: ${token.take(10)}...")
        
        return try {
            val response = api.getArticles()
            Log.d(TAG, "Article fetch response code: ${response.code()}")
            
            if (response.isSuccessful) {
                response.body()?.let { articles ->
                    Log.d(TAG, "Successfully fetched ${articles.articles.size} articles")
                    ArticleResult.Success(articles.articles)
                } ?: run {
                    Log.e(TAG, "Response body is null")
                    ArticleResult.Error("No articles found")
                }
            } else {
                Log.e(TAG, "Failed to fetch articles: ${response.code()}")
                ArticleResult.Error("Failed to fetch articles", response.code())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching articles", e)
            ArticleResult.Error("Network error: ${e.message}")
        }
    }
}
