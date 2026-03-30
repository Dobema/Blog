package com.example.blog.api

data class CommentResponse(
    val id: Long,
    val author: String,
    val content: String,
    val createdAt: String
)
