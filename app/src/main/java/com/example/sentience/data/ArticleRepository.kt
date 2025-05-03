package com.example.sentience.data

import android.util.Log
import com.example.sentience.model.*
import com.example.sentience.network.SentienceApi
import com.example.sentience.util.TokenManager
import retrofit2.Response

class ArticleRepository(private val api: SentienceApi){
    suspend fun fetchArticles(): List<ArticleItem> = api.getArticles()
}