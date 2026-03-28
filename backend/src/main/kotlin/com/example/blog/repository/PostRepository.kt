package com.example.blog.repository

import com.example.blog.domain.PostEntity
import com.example.blog.domain.PostStatus
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long> {
    fun findAllByStatusOrderByPublishedAtDesc(status: PostStatus): List<PostEntity>
    fun findBySlug(slug: String): PostEntity?
    fun findAllByAuthorUsernameOrderByUpdatedAtDesc(username: String): List<PostEntity>
}
