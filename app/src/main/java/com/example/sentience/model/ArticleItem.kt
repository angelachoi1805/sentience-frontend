package com.example.sentience.model

data class ArticleItem(
    val id: Int,
    val title: String,
    val body: String,
    val author: String,
    val created_at: String,
    val readTimeMinutes: Int = 5 // Default value, can be calculated from body length
)

