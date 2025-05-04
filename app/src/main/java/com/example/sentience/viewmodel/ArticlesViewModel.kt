package com.example.sentience.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.*
import com.example.sentience.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class ArticlesViewModel(private val repo: ArticleRepository) : ViewModel() {
    companion object {
        private const val TAG = "ArticlesViewModel"
    }

    private val _articles = MutableStateFlow<List<ArticleItem>>(emptyList())
    val articles: StateFlow<List<ArticleItem>> = _articles

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadArticles()
    }

    fun loadArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            when (val result = repo.fetchArticles()) {
                is ArticleResult.Success -> {
                    Log.d(TAG, "Successfully loaded ${result.articles.size} articles")
                    _articles.value = result.articles
                }
                is ArticleResult.Error -> {
                    Log.e(TAG, "Error loading articles: ${result.message}")
                    _error.value = result.message
                }
            }
            
            _isLoading.value = false
        }
    }
}
