package com.example.blog.api

data class PostPreviewResponse(
    val id: Long,
    val title: String,
    val slug: String,
    val excerpt: String,
    val category: String,
    val author: String,
    val publishedAt: String?
)

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val slug: String,
    val excerpt: String,
    val content: String,
    val status: String,
    val author: String,
    val publishedAt: String?
)
