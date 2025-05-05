package com.example.sentience.data

import android.util.Log
import com.example.sentience.model.*
import com.example.sentience.network.EstimateTestRequest
import com.example.sentience.network.EstimateTestResponse
import com.example.sentience.network.SentienceApi
import com.example.sentience.network.TestDetailsResponse
import com.example.sentience.util.TokenManager
import retrofit2.Response

sealed class TestResult {
    data class Success(val tests: List<TestItem>) : TestResult()
    data class DetailsSuccess(val details: TestDetailsResponse) : TestResult()
    data class EstimateSuccess(val response: EstimateTestResponse) : TestResult()
    data class Error(val message: String, val code: Int? = null) : TestResult()
}


/**
 * Repository class for handling test-related operations.
 * @param api The API service interface for making network calls
 * @param tokenManager Manages authentication tokens
 */
class TestRepository(private val api: SentienceApi, private val tokenManager: TokenManager) {
    companion object {
        private const val TAG = "TestRepository"
    }

    /**
     * Fetches a list of tests from the server.
     * @return TestResult containing either the list of tests or an error
     */
    suspend fun fetchTests(): TestResult {
        val token = tokenManager.getToken() ?: run {
            Log.e(TAG, "Authentication token not found")
            return TestResult.Error("Authentication token not found")
        }

        if (token.isBlank()) {
            Log.e(TAG, "Authentication token is blank")
            return TestResult.Error("Authentication token is blank")
        }

        Log.d(TAG, "Fetching tests with token: ${token.take(10)}...")

        return try {
            val response = api.getTests()
            Log.d(TAG, "Test fetch response code: ${response.code()}")

            if (response.isSuccessful) {
                response.body()?.let { tests ->
                    Log.d(TAG, "Successfully fetched ${tests.tests.size} tests")
                    TestResult.Success(tests.tests)
                } ?: run {
                    Log.e(TAG, "Response body is null")
                    TestResult.Error("No tests found")
                }
            } else {
                Log.e(TAG, "Failed to fetch tests: ${response.code()}")
                TestResult.Error("Failed to fetch tests", response.code())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching tests", e)
            TestResult.Error("Network error: ${e.message}")
        }
    }
    suspend fun fetchTestDetails(id: Int): TestResult {
        val token = tokenManager.getToken() ?: run {
            Log.e(TAG, "Authentication token not found")
            return TestResult.Error("Authentication token not found")
        }

        if (token.isBlank()) {
            Log.e(TAG, "Authentication token is blank")
            return TestResult.Error("Authentication token is blank")
        }

        Log.d(TAG, "Fetching test details for id=$id with token: ${token.take(10)}...")

        return try {
            val response = api.getTestDetails(id)
            Log.d(TAG, "Test details response code: ${response.code()}")

            if (response.isSuccessful) {
                response.body()?.let { details ->
                    Log.d(TAG, "Successfully fetched test details for id=$id")
                    TestResult.DetailsSuccess(details)
                } ?: run {
                    Log.e(TAG, "Details response body is null")
                    TestResult.Error("No details found for test id: $id")
                }
            } else {
                Log.e(TAG, "Failed to fetch test details: ${response.code()}")
                TestResult.Error("Failed to fetch test details", response.code())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching test details", e)
            TestResult.Error("Network error: ${e.message}")
        }
    }
    /**
     * Submits the test answers and returns the estimation result.
     * @param testId The ID of the test
     * @param answers A map of question IDs to selected option IDs
     * @return TestResult containing either the estimation result or an error
     */
    suspend fun submitTestAnswers(testId: Int, answers: Map<Int, Int>): TestResult {
        val token = tokenManager.getToken() ?: run {
            Log.e(TAG, "Authentication token not found")
            return TestResult.Error("Authentication token not found")
        }

        if (token.isBlank()) {
            Log.e(TAG, "Authentication token is blank")
            return TestResult.Error("Authentication token is blank")
        }

        Log.d(TAG, "Submitting test answers for testId=$testId with data: ${answers}...")

        val estimateTestRequest = EstimateTestRequest(answers)

        return try {
            val response = api.estimateTest(testId, estimateTestRequest)
            Log.d(TAG, "Submit test answers response code: ${response.code()}")

            if (response.isSuccessful) {
                response.body()?.let { result ->
                    Log.d(TAG, "Successfully submitted test answers with score: ${result.result.score}")
                    TestResult.EstimateSuccess(result)
                } ?: run {
                    Log.e(TAG, "Response body is null")
                    TestResult.Error("Failed to submit test answers")
                }
            } else {
                Log.e(TAG, "Failed to submit test answers: ${response.code()}")
                TestResult.Error("Failed to submit test answers", response.code())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error submitting test answers", e)
            TestResult.Error("Network error: ${e.message}")
        }
    }
}

/**
 * Exception thrown when authentication-related issues occur.
 */
class AuthenticationException(message: String) : Exception(message)

/**
 * Exception thrown when network-related issues occur.
 */
class NetworkException(message: String) : Exception(message)

