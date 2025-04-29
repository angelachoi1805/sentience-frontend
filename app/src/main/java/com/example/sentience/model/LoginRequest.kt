package com.example.sentience.model

class LoginRequest(
    username: String,
    password: String
) : AuthRequest(username, password)