package com.example.blog.api

data class ProfilePostResponse(
    val id: Long,
    val title: String,
    val status: String,
    val updatedAt: String
)

data class ProfileResponse(
    val username: String,
    val bio: String?,
    val avatarUrl: String?,
    val totalPosts: Int,
    val publishedPosts: Int,
    val draftPosts: Int,
    val posts: List<ProfilePostResponse>
)
