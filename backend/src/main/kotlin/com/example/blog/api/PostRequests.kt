package com.example.blog.api

// Diese Request-Daten kommen aus dem Formular zum Erstellen eines neuen Beitrags.
data class CreatePostRequest(
    val title: String,
    val excerpt: String,
    val content: String,
    val publish: Boolean
)
