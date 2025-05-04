package com.example.sentience.network

import android.util.Log
import com.example.sentience.util.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8000/"
    private const val TAG = "RetrofitInstance"
    private const val TIMEOUT_SECONDS = 30L

    fun create(tokenManager: TokenManager): SentienceApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            val token = tokenManager.getToken()
            
            Log.d(TAG, "Making request to: ${request.url}")
            Log.d(TAG, "Token status: ${if (token != null) "Present" else "Not present"}")
            
            if (token != null) {
                val authRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                Log.d(TAG, "Added Authorization header with token: Bearer ${token.take(10)}...")
                Log.d(TAG, "Full Authorization header: Bearer $token")
                chain.proceed(authRequest)
            } else {
                Log.w(TAG, "No token available for request to ${request.url}")
                chain.proceed(request)
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SentienceApi::class.java)
    }
}
