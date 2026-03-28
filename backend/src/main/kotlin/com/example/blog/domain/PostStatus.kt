package com.example.blog.domain

// PUBLISHED-Beitraege erscheinen auf der Startseite, DRAFT-Beitraege nur intern.
enum class PostStatus {
    DRAFT,
    PUBLISHED
}
