package com.example.blog.api

// Der User wird separat zurueckgegeben, damit das Frontend ihn lokal speichern kann.
data class AuthUserResponse(
    val id: Long,
    val username: String,
    val email: String
)

data class AuthResponse(
    val message: String,
    val user: AuthUserResponse
)
