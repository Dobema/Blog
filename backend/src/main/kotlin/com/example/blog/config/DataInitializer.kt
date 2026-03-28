package com.example.blog.config

import com.example.blog.domain.PostEntity
import com.example.blog.domain.PostStatus
import com.example.blog.domain.UserEntity
import com.example.blog.repository.PostRepository
import com.example.blog.repository.UserRepository
import java.time.Instant
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitializer {
    @Bean
    fun seedDatabase(
        userRepository: UserRepository,
        postRepository: PostRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            if (userRepository.count() > 0L || postRepository.count() > 0L) {
                return@CommandLineRunner
            }

            val author = userRepository.save(
                UserEntity(
                    username = "matthias",
                    email = "matthias@example.com",
                    passwordHash = "demo-placeholder-hash",
                    bio = "Ich schreibe ueber digitale Projekte, Design und Entwicklung.",
                    avatarUrl = null,
                    createdAt = Instant.parse("2026-03-20T10:00:00Z")
                )
            )

            postRepository.saveAll(
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
        }
    }
}
