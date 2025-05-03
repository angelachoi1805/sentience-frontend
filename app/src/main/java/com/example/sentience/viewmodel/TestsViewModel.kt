package com.example.sentience.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.*
import com.example.sentience.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestsViewModel(repo: TestRepository): ViewModel() {
    private val _tests = MutableStateFlow<List<TestItem>>(emptyList())
    val tests: StateFlow<List<TestItem>> = _tests

    init {
        viewModelScope.launch { _tests.value = repo.fetchTests() }
    }
}