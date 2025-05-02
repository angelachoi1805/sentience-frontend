package com.example.sentience.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.sentience.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    username: String,
    viewModel: ChatViewModel
) {
    var message by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf(listOf<String>()) }
    val sendMessageResult by viewModel.sendMessageResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(messages.reversed()) { msg ->
                ChatBubble(text = msg, isUser = true)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    ChatBubble(sendMessageResult ?: "", false)
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
                placeholder = { Text("Введите сообщение... $username") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (message.text.isNotBlank()) {
                    viewModel.askAI(message.text)
                    messages = messages + message.text
                    message = TextFieldValue("")
                }
            }) {
                Text("Отправить")
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
            color = if (isUser) Color(0xFFDCF8C6) else Color(0xFFECECEC),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 2.dp
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(12.dp),
                color = Color.Black
            )
        }
    }
}