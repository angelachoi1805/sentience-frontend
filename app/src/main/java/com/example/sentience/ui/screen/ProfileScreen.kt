package com.example.sentience.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sentience.model.UserTestResultItem
import com.example.sentience.ui.theme.*
import com.example.sentience.viewmodel.ProfileViewModel

data class TestResult(
    val testName: String,
    val score: Int,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    username: String,
    profileViewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val results by profileViewModel.results.collectAsState()
    LaunchedEffect(Unit) {
        profileViewModel.getResults()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile - $username") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Your Test Results",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (results.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("You haven't taken any tests yet.")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(results) { result ->
                        ResultCard(result)
                    }
                }
            }
        }
    }
}

@Composable
fun ResultCard(result: UserTestResultItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = primaryContainerLight)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = result.result_text, style = MaterialTheme.typography.titleMedium)
            Text(text = "Score: ${result.total_score}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Date: ${result.created_at}", style = MaterialTheme.typography.bodySmall)
        }
    }
}