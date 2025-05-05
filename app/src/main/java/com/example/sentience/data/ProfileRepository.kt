package com.example.sentience.data

import com.example.sentience.model.UserTestResponse
import com.example.sentience.network.SentienceApi
import retrofit2.Response

class ProfileRepository (private val api: SentienceApi){
    suspend fun getTestResults(): Response<UserTestResponse> {
        return api.getTestResults()
    }
}