package com.example.blog.service

import com.example.blog.api.PostDetailResponse
import com.example.blog.api.PostPreviewResponse
import com.example.blog.api.ProfilePostResponse
import com.example.blog.api.ProfileResponse
import com.example.blog.domain.PostEntity
import com.example.blog.domain.PostStatus
import com.example.blog.repository.PostRepository
import com.example.blog.repository.UserRepository
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class BlogQueryService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(ZoneId.of("Europe/Berlin"))

    fun getHomepagePosts(): List<PostPreviewResponse> {
        return postRepository.findAllByStatusOrderByPublishedAtDesc(PostStatus.PUBLISHED)
            .map { post -> post.toPreviewResponse() }
    }

    fun getPostBySlug(slug: String): PostDetailResponse {
        val post = postRepository.findBySlug(slug)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Beitrag nicht gefunden.")

        return PostDetailResponse(
            id = post.id ?: 0,
            title = post.title,
            slug = post.slug,
            excerpt = post.excerpt,
            content = post.content,
            status = post.status.name,
            author = post.author.username,
            publishedAt = post.publishedAt?.let(dateFormatter::format)
        )
    }

    fun getProfileByUsername(username: String): ProfileResponse {
        val user = userRepository.findByUsername(username)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Profil nicht gefunden.")
        val posts = postRepository.findAllByAuthorUsernameOrderByUpdatedAtDesc(username)

        return ProfileResponse(
            username = user.username,
            email = user.email,
            bio = user.bio,
            avatarUrl = user.avatarUrl,
            totalPosts = posts.size,
            publishedPosts = posts.count { it.status == PostStatus.PUBLISHED },
            draftPosts = posts.count { it.status == PostStatus.DRAFT },
            posts = posts.map { post ->
                ProfilePostResponse(
                    id = post.id ?: 0,
                    title = post.title,
                    status = post.status.name,
                    updatedAt = dateFormatter.format(post.updatedAt)
                )
            }
        )
    }

    private fun PostEntity.toPreviewResponse(): PostPreviewResponse {
        return PostPreviewResponse(
            id = id ?: 0,
            title = title,
            slug = slug,
            excerpt = excerpt,
            category = "Blog",
            author = author.username,
            publishedAt = publishedAt?.let(dateFormatter::format)
        )
    }
}
