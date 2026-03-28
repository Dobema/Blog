package com.example.blog.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

// Fuer den Anfang brauchen wir nur einen Password-Encoder, noch keine komplette Security-Konfiguration.
@Configuration
class SecurityBeans {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
