package com.example.blog.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, unique = true, length = 50)
    var username: String = "",
    @Column(nullable = false, unique = true, length = 150)
    var email: String = "",
    @Column(nullable = false, length = 255)
    var passwordHash: String = "",
    @Column(length = 400)
    var bio: String? = null,
    @Column(length = 255)
    var avatarUrl: String? = null,
    @Column(nullable = false)
    var createdAt: Instant = Instant.now()
)
