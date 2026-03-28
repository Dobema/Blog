package com.example.blog.controller

import com.example.blog.api.CreatePostRequest
import com.example.blog.api.PostDetailResponse
import com.example.blog.api.PostPreviewResponse
import com.example.blog.config.SessionKeys
import com.example.blog.service.BlogCommandService
import com.example.blog.service.BlogQueryService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

// Liefert Daten fuer die Startseite und spaeter auch fuer Beitragsdetails.
@RestController
@RequestMapping("/api/posts")
class PostController(
    private val blogQueryService: BlogQueryService,
    private val blogCommandService: BlogCommandService
) {
    @GetMapping
    fun listPublishedPosts(): List<PostPreviewResponse> {
        return blogQueryService.getHomepagePosts()
    }

    @GetMapping("/{slug}")
    fun getPost(@PathVariable slug: String): PostDetailResponse {
        return blogQueryService.getPostBySlug(slug)
    }

    @PostMapping
    fun createPost(@RequestBody request: CreatePostRequest, session: HttpSession): PostDetailResponse {
        val userId = session.getAttribute(SessionKeys.USER_ID) as? Long
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Du musst eingeloggt sein, um einen Beitrag anzulegen.")

        return blogCommandService.createPost(userId, request)
    }
}
