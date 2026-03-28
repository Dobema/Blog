package com.example.blog.controller

import com.example.blog.api.AuthResponse
import com.example.blog.api.AuthUserResponse
import com.example.blog.api.LoginRequest
import com.example.blog.api.RegisterRequest
import com.example.blog.config.SessionKeys
import com.example.blog.service.AuthService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

// Dieser Controller verbindet Login und Registrierung mit dem AuthService.
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest, session: HttpSession): AuthResponse {
        val response = authService.register(request)
        session.setAttribute(SessionKeys.USER_ID, response.user.id)
        return response
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest, session: HttpSession): AuthResponse {
        val response = authService.login(request)
        session.setAttribute(SessionKeys.USER_ID, response.user.id)
        return response
    }

    @GetMapping("/me")
    fun me(session: HttpSession): AuthUserResponse {
        val userId = session.getAttribute(SessionKeys.USER_ID) as? Long
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Keine aktive Session gefunden.")

        return authService.getUserResponseById(userId)
    }

    @PostMapping("/logout")
    fun logout(session: HttpSession): Map<String, String> {
        session.invalidate()
        return mapOf("message" to "Logout erfolgreich.")
    }
}
