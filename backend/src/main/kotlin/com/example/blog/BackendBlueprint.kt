package com.example.blog

/*
PSEUDOCODE-BLUEPRINT FUER DAS BLOG-BACKEND

Ziel:
- Authentifizierung fuer Login und Registrierung
- Benutzerprofil mit eigenen Daten und eigenen Beitraegen
- Oeffentliche Blogbeitraege fuer die Startseite

1. Domain-Modelle

data class User(
    id: Long,
    username: String,
    email: String,
    passwordHash: String,
    bio: String?,
    avatarUrl: String?,
    createdAt: Instant
)

data class Post(
    id: Long,
    authorId: Long,
    title: String,
    slug: String,
    excerpt: String,
    content: String,
    status: PostStatus, // DRAFT | PUBLISHED
    publishedAt: Instant?,
    updatedAt: Instant
)

2. Repositories

interface UserRepository {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
    fun findById(id: Long): User?
    fun save(user: User): User
}

interface PostRepository {
    fun findPublishedPosts(page: Int, size: Int): List<Post>
    fun findPublishedPostBySlug(slug: String): Post?
    fun findPostsByAuthor(authorId: Long): List<Post>
    fun save(post: Post): Post
}

3. Services

class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService
) {
    fun register(request: RegisterRequest): AuthResponse {
        // 1. Pruefen, ob E-Mail oder Username schon existieren
        // 2. Passwort hashen
        // 3. User speichern
        // 4. Token erzeugen
        // 5. AuthResponse zurueckgeben
    }

    fun login(request: LoginRequest): AuthResponse {
        // 1. User ueber E-Mail oder Username laden
        // 2. Passwort pruefen
        // 3. Token oder Session erzeugen
        // 4. AuthResponse zurueckgeben
    }
}

class ProfileService(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    fun getOwnProfile(currentUserId: Long): ProfileResponse {
        // 1. User laden
        // 2. Eigene Posts laden
        // 3. Statistiken berechnen
        // 4. Profilantwort aufbauen
    }
}

class PostService(
    private val postRepository: PostRepository
) {
    fun getHomepagePosts(): List<PostPreviewResponse> {
        // Nur PUBLISHED-Posts fuer die Startseite liefern
    }

    fun createPost(currentUserId: Long, request: CreatePostRequest): PostResponse {
        // 1. Eingaben validieren
        // 2. Slug erzeugen
        // 3. Post als DRAFT oder PUBLISHED speichern
        // 4. Response zurueckgeben
    }
}

4. Controller-Endpunkte

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): AuthResponse

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): AuthResponse
}

@RestController
@RequestMapping("/api/profile")
class ProfileController {
    @GetMapping("/me")
    fun me(@AuthenticationPrincipal user: AuthUser): ProfileResponse
}

@RestController
@RequestMapping("/api/posts")
class PostController {
    @GetMapping
    fun listPublishedPosts(): List<PostPreviewResponse>

    @GetMapping("/{slug}")
    fun getPost(@PathVariable slug: String): PostResponse

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal user: AuthUser,
        @RequestBody request: CreatePostRequest
    ): PostResponse
}

5. Frontend-Anbindung

- Startseite:
  GET /api/posts
  -> liefert alle veroeffentlichten Beitraege fuer den unteren Feed.

- Login-Seite:
  POST /api/auth/login
  -> liefert Token oder Session-Info.

- Registrierungsseite:
  POST /api/auth/register
  -> legt User an und loggt optional direkt ein.

- Profilseite:
  GET /api/profile/me
  -> liefert Basisdaten und eigene Beitraege.

6. Naechste echte Implementierungsschritte

- Spring Security konfigurieren
- Passwort-Hashing mit BCrypt
- JWT oder Session-basierte Authentifizierung
- Datenbank mit JPA oder Exposed anbinden
- DTOs, Validation und Exception-Handling ergaenzen
*/
object BackendBlueprint
