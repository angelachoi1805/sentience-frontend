package com.example.sentience.util

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? {
        return "Bearer ${prefs.getString("jwt_token", null)}"
    }

    fun clearToken() {
        prefs.edit().remove("jwt_token").apply()
    }
}
