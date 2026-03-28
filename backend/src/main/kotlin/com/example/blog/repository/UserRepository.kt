package com.example.blog.repository

import com.example.blog.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

// Spring Data erzeugt aus diesen Methodennamen automatisch die noetigen Datenbankabfragen.
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun findByEmail(email: String): UserEntity?
    fun findByUsername(username: String): UserEntity?
}
