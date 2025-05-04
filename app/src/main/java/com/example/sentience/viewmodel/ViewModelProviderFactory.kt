package com.example.sentience.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sentience.data.*

class AuthViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {
    companion object {
        private const val TAG = "AuthViewModelFactory"
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                Log.d(TAG, "Creating AuthViewModel")
                AuthViewModel(repository) as T
            } else {
                Log.e(TAG, "Unknown ViewModel class: ${modelClass.name}")
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating AuthViewModel", e)
            throw e
        }
    }
}

class ArticlesViewModelFactory(
    private val repository: ArticleRepository
) : ViewModelProvider.Factory {
    companion object {
        private const val TAG = "ArticlesViewModelFactory"
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            if (modelClass.isAssignableFrom(ArticlesViewModel::class.java)) {
                Log.d(TAG, "Creating ArticlesViewModel")
                ArticlesViewModel(repository) as T
            } else {
                Log.e(TAG, "Unknown ViewModel class: ${modelClass.name}")
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating ArticlesViewModel", e)
            throw e
        }
    }
}

class TestsViewModelFactory(
    private val repository: TestRepository
) : ViewModelProvider.Factory {
    companion object {
        private const val TAG = "TestsViewModelFactory"
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            if (modelClass.isAssignableFrom(TestsViewModel::class.java)) {
                Log.d(TAG, "Creating TestsViewModel")
                TestsViewModel(repository) as T
            } else {
                Log.e(TAG, "Unknown ViewModel class: ${modelClass.name}")
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating TestsViewModel", e)
            throw e
        }
    }
}


