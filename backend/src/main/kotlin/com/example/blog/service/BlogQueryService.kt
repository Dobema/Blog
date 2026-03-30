package com.example.blog.service

import com.example.blog.api.CommentResponse
import com.example.blog.api.PostDetailResponse
import com.example.blog.api.PostPreviewResponse
import com.example.blog.api.ProfilePostResponse
import com.example.blog.api.ProfileResponse
import com.example.blog.domain.PostEntity
import com.example.blog.domain.PostStatus
import com.example.blog.repository.CommentRepository
import com.example.blog.repository.PostRepository
import com.example.blog.repository.UserRepository
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional(readOnly = true)
class BlogQueryService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    // Alle Datumswerte werden hier zentral formatiert, damit die API konsistent bleibt.
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
            publishedAt = post.publishedAt?.let(dateFormatter::format),
            comments = commentRepository.findAllByPostSlugOrderByCreatedAtAsc(slug).map { comment ->
                CommentResponse(
                    id = comment.id ?: 0,
                    author = comment.author.username,
                    content = comment.content,
                    createdAt = dateFormatter.format(comment.createdAt)
                )
            }
        )
    }

    fun getProfileByUsername(username: String): ProfileResponse {
        // Erst das Profil laden, danach die dazugehoerigen Beitraege holen.
        val user = userRepository.findByUsername(username)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Profil nicht gefunden.")
        return buildProfileResponse(user.username, user.bio, user.avatarUrl)
    }

    fun getProfileByUserId(userId: Long): ProfileResponse {
        val user = userRepository.findById(userId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Profil nicht gefunden.")
        }

        return buildProfileResponse(user.username, user.bio, user.avatarUrl)
    }

    private fun buildProfileResponse(
        username: String,
        bio: String?,
        avatarUrl: String?
    ): ProfileResponse {
        val posts = postRepository.findAllByAuthorUsernameOrderByUpdatedAtDesc(username)

        return ProfileResponse(
            username = username,
            bio = bio,
            avatarUrl = avatarUrl,
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
        // Entities werden bewusst in Response-Objekte uebersetzt,
        // damit die API nicht von den Datenbankklassen abhaengig bleibt.
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
