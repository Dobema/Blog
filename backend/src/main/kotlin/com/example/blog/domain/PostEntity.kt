package com.example.blog.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "posts")
class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    var author: UserEntity = UserEntity(),
    @Column(nullable = false, length = 180)
    var title: String = "",
    @Column(nullable = false, unique = true, length = 180)
    var slug: String = "",
    @Column(nullable = false, length = 500)
    var excerpt: String = "",
    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String = "",
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: PostStatus = PostStatus.DRAFT,
    @Column(nullable = true)
    var publishedAt: Instant? = null,
    @Column(nullable = false)
    var updatedAt: Instant = Instant.now()
)
