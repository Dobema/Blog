package com.example.blog.controller

import com.example.blog.config.SessionKeys
import com.example.blog.api.ProfileResponse
import com.example.blog.service.BlogQueryService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

// Das Profil wird aktuell ueber den Benutzernamen aus der URL geladen.
@RestController
@RequestMapping("/api/profiles")
class ProfileController(
    private val blogQueryService: BlogQueryService
) {
    @GetMapping("/me")
    fun getOwnProfile(session: HttpSession): ProfileResponse {
        val userId = session.getAttribute(SessionKeys.USER_ID) as? Long
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Keine aktive Session gefunden.")

        return blogQueryService.getProfileByUserId(userId)
    }

    @GetMapping("/{username}")
    fun getProfile(@PathVariable username: String): ProfileResponse {
        return blogQueryService.getProfileByUsername(username)
    }
}
