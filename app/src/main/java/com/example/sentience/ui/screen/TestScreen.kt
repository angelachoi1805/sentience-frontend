package com.example.sentience.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sentience.model.TestItem
import com.example.sentience.network.EstimateTestRequest
import com.example.sentience.viewmodel.TestsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    test: TestItem,
    onNavigateBack: () -> Unit,
    testsViewModel: TestsViewModel
) {
    val testDetails by testsViewModel.selectedTestDetails.collectAsState()
    val estimateResult by testsViewModel.estimateResult.collectAsState()

    val selectedAnswers = remember { mutableStateMapOf<Int, Int>() }

    LaunchedEffect(test) {
        testsViewModel.loadTestDetails(test.id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(test.title) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (testDetails == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                return@Column
            }

            // Вопросы и варианты
            testDetails?.questions?.forEach { item ->
                val question = item.question
                val options = item.options

                Text(
                    text = question.text,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedAnswers[question.id] == option.id,
                            onClick = { selectedAnswers[question.id] = option.id }
                        )
                        Text(
                            text = option.option_text,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка Submit
            Button(
                onClick = {
                    val answers = selectedAnswers.toMap()

                    testsViewModel.submitTest(test.id, answers )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = selectedAnswers.size == testDetails?.questions?.size
            ) {
                Text("Submit")
            }

            // Результат после отправки
            estimateResult?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Score: ${it.result.score}",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}