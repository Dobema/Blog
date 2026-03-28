package com.example.blog.service

import com.example.blog.api.AuthResponse
import com.example.blog.api.AuthUserResponse
import com.example.blog.api.LoginRequest
import com.example.blog.api.RegisterRequest
import com.example.blog.domain.UserEntity
import com.example.blog.repository.UserRepository
import java.time.Instant
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun register(request: RegisterRequest): AuthResponse {
        val username = request.username.trim()
        val email = request.email.trim().lowercase()
        val password = request.password.trim()

        // Diese Checks ersetzen fuer den Moment eine groessere Validation-Schicht.
        if (username.length < 3) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Der Benutzername muss mindestens 3 Zeichen lang sein.")
        }
        if (password.length < 8) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Das Passwort muss mindestens 8 Zeichen lang sein.")
        }
        if (userRepository.existsByUsername(username)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Der Benutzername ist bereits vergeben.")
        }
        if (userRepository.existsByEmail(email)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Die E-Mail-Adresse ist bereits registriert.")
        }

        val savedUser = userRepository.save(
            UserEntity(
                username = username,
                email = email,
                passwordHash = passwordEncoder.encode(password),
                bio = "Neues Mitglied im Blog-Projekt.",
                avatarUrl = null,
                createdAt = Instant.now()
            )
        )

        return AuthResponse(
            message = "Konto erfolgreich angelegt.",
            user = savedUser.toAuthUserResponse()
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        val email = request.email.trim().lowercase()
        val password = request.password.trim()
        val user = userRepository.findByEmail(email)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-Mail oder Passwort ist nicht korrekt.")

        if (!passwordEncoder.matches(password, user.passwordHash)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-Mail oder Passwort ist nicht korrekt.")
        }

        return AuthResponse(
            message = "Login erfolgreich.",
            user = user.toAuthUserResponse()
        )
    }

    fun getUserResponseById(userId: Long): AuthUserResponse {
        val user = userRepository.findById(userId).orElseThrow {
            ResponseStatusException(HttpStatus.UNAUTHORIZED, "Keine aktive Session gefunden.")
        }

        return user.toAuthUserResponse()
    }

    private fun UserEntity.toAuthUserResponse(): AuthUserResponse {
        return AuthUserResponse(
            id = id ?: 0,
            username = username,
            email = email
        )
    }
}
