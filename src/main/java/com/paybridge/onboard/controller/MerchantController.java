package com.paybridge.onboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paybridge.onboard.dto.MerchantOnboardRequest;
import com.paybridge.onboard.dto.MerchantOnboardResponse;
import com.paybridge.onboard.service.MerchantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {
	private final MerchantService merchantService;
	
	@PostMapping
    public ResponseEntity<MerchantOnboardResponse> onboardMerchant(
            @Valid @RequestBody MerchantOnboardRequest request) {
        MerchantOnboardResponse response = merchantService.onboardMerchant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
}
