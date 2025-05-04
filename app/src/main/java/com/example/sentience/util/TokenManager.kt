package com.example.sentience.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class TokenManager(context: Context) {

    companion object {
        private const val TAG = "TokenManager"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        // Remove Bearer prefix if present
        val rawToken = if (token.startsWith("Bearer ")) token.substring(7) else token
        Log.d(TAG, "Saving raw token: $rawToken")
        prefs.edit().putString("jwt_token", rawToken).apply()
    }

    fun getToken(): String? {
        val token = prefs.getString("jwt_token", null)
        Log.d(TAG, "Retrieved raw token: $token")
        return token
    }

    fun clearToken() {
        prefs.edit().remove("jwt_token").apply()
    }
}
