package com.example.sentience.data

import android.util.Log
import com.example.sentience.model.*
import com.example.sentience.network.SentienceApi
import com.example.sentience.util.TokenManager
import retrofit2.Response

class AuthRepository(
    private val api: SentienceApi,
    private val tokenManager: TokenManager
) {
    companion object {
        private const val TAG = "AuthRepository"
    }

    suspend fun login(username: String, password: String): Response<TokenResponse> {
        return try {
            Log.d(TAG, "Attempting login for user: $username")
            val response = api.login(LoginRequest(username, password))
            
            if (response.isSuccessful) {
                response.body()?.access_token?.let { token ->
                    Log.d(TAG, "Login successful, saving token")
                    tokenManager.saveToken(token)
                } ?: run {
                    Log.e(TAG, "Login response body is null")
                }
            } else {
                Log.e(TAG, "Login failed with code: ${response.code()}")
            }
            response
        } catch (e: Exception) {
            Log.e(TAG, "Login error: ${e.message}", e)
            throw e
        }
    }

    suspend fun register(username: String, password: String, profilePic: String): Response<TokenResponse> {
        return try {
            Log.d(TAG, "Attempting registration for user: $username")
            val response = api.register(RegisterRequest(username, password, profilePic))
            
            if (response.isSuccessful) {
                response.body()?.access_token?.let { token ->
                    Log.d(TAG, "Registration successful, saving token")
                    tokenManager.saveToken(token)
                } ?: run {
                    Log.e(TAG, "Registration response body is null")
                }
            } else {
                Log.e(TAG, "Registration failed with code: ${response.code()}")
            }
            response
        } catch (e: Exception) {
            Log.e(TAG, "Registration error: ${e.message}", e)
            throw e
        }
    }

    suspend fun getUserProfile(): Response<UserProfile> {
        return try {
            val token = tokenManager.getToken()
            if (token == null) {
                Log.e(TAG, "No token available for profile request")
                throw AuthenticationException("No authentication token available")
            }
            
            Log.d(TAG, "Fetching user profile with token: ${token.take(10)}...")
            api.getProfile()
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching user profile: ${e.message}", e)
            throw e
        }
    }
}
