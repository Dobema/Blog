package com.example.blog

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// Antwortmodell fuer einen sehr einfachen Health-Check.
data class HealthResponse(
    val status: String,
    val message: String
)

// Damit kann das Frontend pruefen, ob das Backend ueberhaupt erreichbar ist.
@RestController
@RequestMapping("/api")
class HealthController {
    @GetMapping("/health")
    fun health(): HealthResponse {
        return HealthResponse(
            status = "OK",
            message = "Das Kotlin-Backend antwortet erfolgreich."
        )
    }
}
