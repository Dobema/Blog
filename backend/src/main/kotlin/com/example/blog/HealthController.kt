package com.example.blog

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class HealthResponse(
    val status: String,
    val message: String
)

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

