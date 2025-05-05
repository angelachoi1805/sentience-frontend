package com.example.sentience.model

data class TestItem(
    val id: Int,
    val title: String,
    val description: String,
    val minutes: Int
)


data class QuestionItem(
    val id: Int,
    val test_id: String,
    val text: String,
    val order_number: Int,
    val options: List<OptionItem>
)

data class OptionItem(
    val id: Int,
    val question_id: Int,
    val option_text: String
)

