package com.example.sentience.model

data class UserProfile(
    val message: String,
    val user: User
)

data class User(
    val user_id: String
)


data class UserTestResultItem(
    val id: Int,
    val user_id: Int,
    val test_id: Int,
    val total_score: Int,
    val result_text: String,
    val created_at: String
)

data class UserTestResponse(
    val results: List<UserTestResultItem>
)