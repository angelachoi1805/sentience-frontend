package com.example.sentience.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.sentience.ui.theme.*
import com.example.sentience.viewmodel.ChatViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    username: String,
    viewModel: ChatViewModel,
    onBack: () -> Unit // функция для навигации назад
) {
    var message by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<Pair<String, String?>>()) }
    val sendMessageResult by viewModel.sendMessageResult.collectAsState()

    LaunchedEffect(sendMessageResult) {
        if (sendMessageResult != null && messages.isNotEmpty()) {
            val last = messages.last()
            messages = messages.dropLast(1) + (last.first to sendMessageResult)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with Sentience") },
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
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                reverseLayout = true
            ) {
                items(messages.reversed()) { (userMsg, aiMsg) ->
                    ChatBubble(text = userMsg, isUser = true)
                    aiMsg?.let {
                        Spacer(modifier = Modifier.height(4.dp))
                        ChatBubble(text = it, isUser = false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Enter your message...") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (message.text.isNotBlank()) {
                        viewModel.askAI(message.text)
                        messages = messages + (message.text to null)
                        message = TextFieldValue("")
                    }
                }) {
                    Text("Send")
                }
            }
        }
    }
}
@Composable
fun ChatBubble(text: String, isUser: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            color = if (isUser) primaryContainerLight else tertiaryContainerLight,
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 2.dp
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(12.dp),
                color = if (isUser) onPrimaryContainerLight else onTertiaryLight
            )
        }
    }
}
