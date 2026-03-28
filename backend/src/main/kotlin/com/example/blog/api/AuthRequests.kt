package com.example.blog.api

// Request-Daten fuer das Registrierungsformular.
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

// Request-Daten fuer das Login-Formular.
data class LoginRequest(
    val email: String,
    val password: String
)
