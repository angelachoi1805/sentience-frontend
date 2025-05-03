package com.example.sentience.model

data class ArticleItem(
    val id: String,
    val title: String,
    val body: String,
    val author: String,
    val readTimeMinutes: Int, // estimate from body length
    val description: String // first ~100 chars of body
)

