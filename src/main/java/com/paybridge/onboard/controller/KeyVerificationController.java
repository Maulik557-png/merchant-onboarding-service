package com.paybridge.onboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paybridge.onboard.dto.KeyVerifyRequest;
import com.paybridge.onboard.dto.KeyVerifyResponse;
import com.paybridge.onboard.service.ApiKeyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/keys")
public class KeyVerificationController {

    private final ApiKeyService apiKeyService;

    @PostMapping("/verify")
    public ResponseEntity<KeyVerifyResponse> verifyKey(@Valid @RequestBody KeyVerifyRequest request) {
    	log.info("verifyKey called in KeyVerificationController");
    	
        KeyVerifyResponse response = apiKeyService.verifyKey(request.getApiKey());
        
        log.info("Key verification result in KeyVerificationController: {}", response.isValid());
        return ResponseEntity.ok(response);
    }
}