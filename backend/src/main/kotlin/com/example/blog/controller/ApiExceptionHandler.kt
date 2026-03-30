package com.example.blog.controller

import com.example.blog.api.ApiErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class ApiExceptionHandler {
    private val logger = LoggerFactory.getLogger(ApiExceptionHandler::class.java)

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(exception: ResponseStatusException): ResponseEntity<ApiErrorResponse> {
        val status = HttpStatus.valueOf(exception.statusCode.value())
        val message = exception.reason ?: "Die Anfrage konnte nicht verarbeitet werden."
        return ResponseEntity.status(status).body(ApiErrorResponse(message))
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(exception: DataIntegrityViolationException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ApiErrorResponse("Benutzername oder E-Mail-Adresse existiert bereits."))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(exception: Exception): ResponseEntity<ApiErrorResponse> {
        logger.error("Unerwarteter Fehler bei der API-Verarbeitung", exception)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiErrorResponse("Im Backend ist ein unerwarteter Fehler aufgetreten."))
    }
}
