package com.paybridge.onboard.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paybridge.onboard.dto.MerchantOnboardRequest;
import com.paybridge.onboard.dto.MerchantOnboardResponse;

@Service
public class MerchantService {

	@Transactional
    public MerchantOnboardResponse onboardMerchant(MerchantOnboardRequest request) {
		String merchantId = "test-merchant-id"; // Replace with actual merchant ID generation logic
        
        // Repository save logic here

        return MerchantOnboardResponse.builder()
                .merchantId(merchantId)
                .apiKey("test-api-key") // Replace with actual API key generation logic
                .environment("SANDBOX")
                .createdAt(Instant.now())
                .build();
    }

}
