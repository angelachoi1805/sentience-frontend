package com.example.sentience.model

class RegisterRequest(
    username: String,
    password: String,
    val profile_pic: String? = null
) : AuthRequest(username, password)