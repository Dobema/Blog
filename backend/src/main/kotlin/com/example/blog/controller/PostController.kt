package com.example.blog.controller

import com.example.blog.api.PostDetailResponse
import com.example.blog.api.PostPreviewResponse
import com.example.blog.service.BlogQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val blogQueryService: BlogQueryService
) {
    @GetMapping
    fun listPublishedPosts(): List<PostPreviewResponse> {
        return blogQueryService.getHomepagePosts()
    }

    @GetMapping("/{slug}")
    fun getPost(@PathVariable slug: String): PostDetailResponse {
        return blogQueryService.getPostBySlug(slug)
    }
}
