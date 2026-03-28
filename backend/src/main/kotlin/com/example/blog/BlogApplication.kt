package com.example.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// Einstiegspunkt fuer die Spring-Boot-Anwendung.
@SpringBootApplication
class BlogApplication

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
}
