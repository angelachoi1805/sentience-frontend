package com.example.sentience.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.*
import com.example.sentience.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class TestsViewModel(private val repo: TestRepository) : ViewModel() {
    companion object {
        private const val TAG = "TestsViewModel"
    }

    private val _tests = MutableStateFlow<List<TestItem>>(emptyList())
    val tests: StateFlow<List<TestItem>> = _tests

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadTests()
    }

    fun loadTests() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            when (val result = repo.fetchTests()) {
                is TestResult.Success -> {
                    Log.d(TAG, "Successfully loaded ${result.tests.size} tests")
                    _tests.value = result.tests
                }
                is TestResult.Error -> {
                    Log.e(TAG, "Error loading tests: ${result.message}")
                    _error.value = result.message
                }
            }
            
            _isLoading.value = false
        }
    }
}
