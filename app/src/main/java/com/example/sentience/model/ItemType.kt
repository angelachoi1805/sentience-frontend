package com.example.sentience.model

sealed class ItemType {
    data class Article(val item: ArticleItem) : ItemType()
    data class Test(val item: TestItem) : ItemType()
}