package com.example.blog.controller

import com.example.blog.api.ProfileResponse
import com.example.blog.service.BlogQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/profiles")
class ProfileController(
    private val blogQueryService: BlogQueryService
) {
    @GetMapping("/{username}")
    fun getProfile(@PathVariable username: String): ProfileResponse {
        return blogQueryService.getProfileByUsername(username)
    }
}
