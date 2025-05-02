package com.example.sentience.model

data class UserProfile(
    val message: String,
    val user: User
)

data class User(
    val user_id: String
)