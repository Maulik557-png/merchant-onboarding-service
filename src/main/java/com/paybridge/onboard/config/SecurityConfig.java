package com.paybridge.onboard.config;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    SecureRandom secureRandom() {
        return new SecureRandom(); 
    }
}