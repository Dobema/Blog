package com.example.blog.service

import com.example.blog.api.CreatePostRequest
import com.example.blog.api.PostDetailResponse
import com.example.blog.domain.PostEntity
import com.example.blog.domain.PostStatus
import com.example.blog.repository.PostRepository
import com.example.blog.repository.UserRepository
import java.text.Normalizer
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class BlogCommandService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(ZoneId.of("Europe/Berlin"))

    fun createPost(currentUserId: Long, request: CreatePostRequest): PostDetailResponse {
        val author = userRepository.findById(currentUserId).orElseThrow {
            ResponseStatusException(HttpStatus.UNAUTHORIZED, "Kein gueltiger Benutzer in der Session gefunden.")
        }

        val title = request.title.trim()
        val excerpt = request.excerpt.trim()
        val content = request.content.trim()

        if (title.length < 5) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Der Titel muss mindestens 5 Zeichen lang sein.")
        }
        if (excerpt.length < 10) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Der Auszug muss mindestens 10 Zeichen lang sein.")
        }
        if (content.length < 20) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Der Inhalt muss mindestens 20 Zeichen lang sein.")
        }

        val now = Instant.now()
        val publishNow = request.publish
        val slug = buildUniqueSlug(title)
        val savedPost = postRepository.save(
            PostEntity(
                author = author,
                title = title,
                slug = slug,
                excerpt = excerpt,
                content = content,
                status = if (publishNow) PostStatus.PUBLISHED else PostStatus.DRAFT,
                publishedAt = if (publishNow) now else null,
                updatedAt = now
            )
        )

        return PostDetailResponse(
            id = savedPost.id ?: 0,
            title = savedPost.title,
            slug = savedPost.slug,
            excerpt = savedPost.excerpt,
            content = savedPost.content,
            status = savedPost.status.name,
            author = author.username,
            publishedAt = savedPost.publishedAt?.let(dateFormatter::format)
        )
    }

    private fun buildUniqueSlug(title: String): String {
        val baseSlug = title.toSlug()
        var slug = baseSlug
        var suffix = 2

        while (postRepository.findBySlug(slug) != null) {
            slug = "$baseSlug-$suffix"
            suffix += 1
        }

        return slug
    }

    private fun String.toSlug(): String {
        val normalized = Normalizer.normalize(lowercase(), Normalizer.Form.NFD)
            .replace("\\p{M}+".toRegex(), "")

        return normalized
            .replace("[^a-z0-9]+".toRegex(), "-")
            .trim('-')
            .ifBlank { "beitrag" }
    }
}
