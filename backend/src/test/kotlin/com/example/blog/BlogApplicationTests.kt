package com.example.blog

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.Test
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BlogApplicationTests {
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun contextLoads() {
    }

    @Test
    fun `logged in users can comment on a published post`() {
        val registerHeaders = HttpHeaders()
        registerHeaders.contentType = MediaType.APPLICATION_JSON
        val uniqueSuffix = UUID.randomUUID().toString().replace("-", "").take(12)
        val username = "tester$uniqueSuffix"
        val email = "$username@example.test"

        val registerResponse = restTemplate.postForEntity(
            "/api/auth/register",
            HttpEntity("""{"username":"$username","email":"$email","password":"Testpasswort123"}""", registerHeaders),
            Map::class.java
        )

        assertEquals(HttpStatus.OK, registerResponse.statusCode)

        val sessionCookie = registerResponse.headers["Set-Cookie"]
            ?.firstOrNull { it.startsWith("JSESSIONID=") }
        assertTrue(sessionCookie != null)

        val commentHeaders = HttpHeaders()
        commentHeaders.contentType = MediaType.APPLICATION_JSON
        commentHeaders.add(HttpHeaders.COOKIE, sessionCookie)

        val commentResponse = restTemplate.postForEntity(
            "/api/posts/idee-blog-eigene-stimme/comments",
            HttpEntity("""{"content":"Ein Testkommentar fuer die neue Kommentarfunktion."}""", commentHeaders),
            Map::class.java
        )

        assertEquals(HttpStatus.OK, commentResponse.statusCode)
        assertEquals(username, commentResponse.body?.get("author"))

        val detailHeaders = HttpHeaders()
        detailHeaders.add(HttpHeaders.COOKIE, sessionCookie)
        val detailResponse = restTemplate.exchange(
            "/api/posts/idee-blog-eigene-stimme",
            HttpMethod.GET,
            HttpEntity<Void>(detailHeaders),
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        assertEquals(HttpStatus.OK, detailResponse.statusCode)
        val comments = detailResponse.body?.get("comments") as? List<*>
        assertTrue(comments != null && comments.isNotEmpty())
        assertTrue(
            comments.any { entry ->
                val comment = entry as? Map<*, *>
                comment?.get("content") == "Ein Testkommentar fuer die neue Kommentarfunktion."
            }
        )
    }
}
