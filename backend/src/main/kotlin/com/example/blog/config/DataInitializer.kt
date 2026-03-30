package com.example.blog.config

import com.example.blog.domain.CommentEntity
import com.example.blog.domain.PostEntity
import com.example.blog.domain.PostStatus
import com.example.blog.domain.UserEntity
import com.example.blog.repository.CommentRepository
import com.example.blog.repository.PostRepository
import com.example.blog.repository.UserRepository
import java.time.Instant
import java.util.UUID
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitializer {
    @Bean
    fun seedDatabase(
        userRepository: UserRepository,
        postRepository: PostRepository,
        commentRepository: CommentRepository,
        passwordEncoder: BCryptPasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner {
            // Seed-Daten nur anlegen, wenn die Datenbank noch leer ist.
            if (userRepository.count() > 0L || postRepository.count() > 0L || commentRepository.count() > 0L) {
                return@CommandLineRunner
            }

            val author = userRepository.save(
                UserEntity(
                    username = "journal-demo",
                    email = "demo@example.invalid",
                    // Die Seed-Inhalte erhalten bewusst keinen bekannten Login.
                    passwordHash = passwordEncoder.encode(UUID.randomUUID().toString()),
                    bio = "Beispielprofil fuer die ersten oeffentlichen Inhalte im Journal.",
                    avatarUrl = null,
                    createdAt = Instant.parse("2026-03-20T10:00:00Z")
                )
            )

            val savedPosts = postRepository.saveAll(
                listOf(
                    PostEntity(
                        author = author,
                        title = "Wie aus einer Idee ein Blog mit eigener Stimme wird",
                        slug = "idee-blog-eigene-stimme",
                        excerpt = "Ein Blick auf Themenfindung und die ersten veroeffentlichten Texte.",
                        content = "Dieser Beispielbeitrag liegt bereits in der Datenbank und zeigt die Struktur fuer echte Inhalte.",
                        status = PostStatus.PUBLISHED,
                        publishedAt = Instant.parse("2026-03-28T08:00:00Z"),
                        updatedAt = Instant.parse("2026-03-28T08:00:00Z")
                    ),
                    PostEntity(
                        author = author,
                        title = "Mein Setup fuer Schreiben, Entwerfen und Verfeinern",
                        slug = "setup-schreiben-entwerfen-verfeinern",
                        excerpt = "Welche Tools und Rituale beim Schreiben und Bauen eines Blogs helfen.",
                        content = "Auch dieser Beitrag ist Seed-Datenmaterial und kann spaeter durch echte Inhalte ersetzt werden.",
                        status = PostStatus.PUBLISHED,
                        publishedAt = Instant.parse("2026-03-26T08:00:00Z"),
                        updatedAt = Instant.parse("2026-03-26T08:00:00Z")
                    ),
                    PostEntity(
                        author = author,
                        title = "Entwurf: Serie ueber React und Kotlin",
                        slug = "entwurf-serie-react-kotlin",
                        excerpt = "Ein noch nicht veroeffentlichter Artikel fuer spaetere Profilansichten.",
                        content = "Dieser Beitrag befindet sich noch im Entwurf und ist daher nicht auf der Startseite sichtbar.",
                        status = PostStatus.DRAFT,
                        publishedAt = null,
                        updatedAt = Instant.parse("2026-03-27T18:30:00Z")
                    )
                )
            )

            val firstPublishedPost = savedPosts.first()
            commentRepository.saveAll(
                listOf(
                    CommentEntity(
                        post = firstPublishedPost,
                        author = author,
                        content = "Freut mich, dass die ersten Beitraege jetzt nicht nur lesbar sind, sondern auch Feedback einsammeln koennen.",
                        createdAt = Instant.parse("2026-03-28T10:15:00Z")
                    ),
                    CommentEntity(
                        post = firstPublishedPost,
                        author = author,
                        content = "Als naechstes lohnt sich bestimmt noch eine kleine Diskussion unter jedem Artikel.",
                        createdAt = Instant.parse("2026-03-28T11:40:00Z")
                    )
                )
            )
        }
    }
}
