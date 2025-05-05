package com.example.sentience.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sentience.data.*
import com.example.sentience.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.example.sentience.network.EstimateTestRequest
import com.example.sentience.network.EstimateTestResponse
import com.example.sentience.network.TestDetailsResponse

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

    // Новый state для деталей теста
    private val _selectedTestDetails = MutableStateFlow<TestDetailsResponse?>(null)
    val selectedTestDetails: StateFlow<TestDetailsResponse?> = _selectedTestDetails


    private val _estimateResult = MutableStateFlow<EstimateTestResponse?>(null)
    val estimateResult: StateFlow<EstimateTestResponse?> = _estimateResult

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

                is TestResult.DetailsSuccess -> TODO()
                is TestResult.EstimateSuccess -> TODO()
            }

            _isLoading.value = false
        }
    }

    fun loadTestDetails(testId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            when (val result = repo.fetchTestDetails(testId)) {
                is TestResult.DetailsSuccess -> {
                    Log.d(TAG, "Loaded test details for ID ${result.details}")
                    _selectedTestDetails.value = result.details
                }
                is TestResult.Error -> {
                    Log.e(TAG, "Failed to load test details: ${result.message}")
                    _error.value = result.message
                }

                is TestResult.Success -> TODO()
                is TestResult.EstimateSuccess -> TODO()
            }

            _isLoading.value = false
        }
    }

    fun submitTest(testId: Int, answers: Map<Int, Int>) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            when (val result = repo.submitTestAnswers(testId, answers)) {
                is TestResult.EstimateSuccess -> {
                    Log.d(TAG, "Test estimated successfully with score: ${result.response.result.score}")
                    _estimateResult.value = result.response
                }
                is TestResult.Error -> {
                    Log.e(TAG, "Failed to estimate test: ${result.message}")
                    _error.value = result.message
                }
                else -> {
                    Log.e(TAG, "Unexpected result in submitTest")
                }
            }

            _isLoading.value = false
        }
    }


}