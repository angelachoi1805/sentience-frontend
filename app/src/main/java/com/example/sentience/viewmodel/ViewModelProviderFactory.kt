package com.example.sentience.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sentience.data.*

class AuthViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ArticlesViewModelFactory(
    private val repo: ArticleRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticlesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticlesViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}

class TestsViewModelFactory(
    private val repo: TestRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestsViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}
