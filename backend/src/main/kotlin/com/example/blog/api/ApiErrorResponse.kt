package com.example.blog.api

// Einheitliches Fehlerformat fuer das Frontend, damit Fehlermeldungen immer lesbar bleiben.
data class ApiErrorResponse(
    val message: String
)
