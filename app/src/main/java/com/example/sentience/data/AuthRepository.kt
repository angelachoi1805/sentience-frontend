package com.example.sentience.data

import com.example.sentience.model.*
import com.example.sentience.network.SentienceApi
import com.example.sentience.util.TokenManager
import retrofit2.Response

class AuthRepository(
    private val api: SentienceApi,
    private val tokenManager: TokenManager
) {
    suspend fun login(username: String, password: String): Response<TokenResponse> {
        val response = api.login(LoginRequest(username, password))
        if (response.isSuccessful) {
            response.body()?.access_token?.let { token ->
                tokenManager.saveToken(token)
            }
        }
        return response
    }

    suspend fun register(username: String, password: String, profilePic: String): Response<TokenResponse> {
        val response = api.register(RegisterRequest(username, password, profilePic))
        if (response.isSuccessful) {
            response.body()?.access_token?.let { token ->
                tokenManager.saveToken(token)
            }
        }
        return response
    }

    suspend fun getUserProfile(): UserProfile {
        return api.getProfile()
    }

}
