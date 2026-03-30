package com.example.blog.repository

import com.example.blog.domain.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<CommentEntity, Long> {
    fun findAllByPostSlugOrderByCreatedAtAsc(slug: String): List<CommentEntity>
}
