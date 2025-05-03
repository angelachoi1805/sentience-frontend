package com.example.sentience.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.*
import com.example.sentience.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticlesViewModel(repo: ArticleRepository): ViewModel() {
    private val _articles = MutableStateFlow<List<ArticleItem>>(emptyList())
    val articles: StateFlow<List<ArticleItem>> = _articles

    init {
        viewModelScope.launch { _articles.value = repo.fetchArticles() }
    }
}