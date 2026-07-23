package com.paybridge.onboard.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paybridge.onboard.constants.OnboardingConstants;
import com.paybridge.onboard.dto.MerchantOnboardRequest;
import com.paybridge.onboard.dto.MerchantOnboardResponse;
import com.paybridge.onboard.entity.MerchantEntity;
import com.paybridge.onboard.repository.MerchantRepository;
import com.paybridge.onboard.service.helper.MerchantIdGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    
    private final ApiKeyService apiKeyService;
    
    private final MerchantIdGenerator merchantIdGenerator;

    // Wraps both inserts (merchant row + key row) atomically — a failure on
    // either side rolls back both, so we never end up with a merchant that
    // has no key to authenticate with.
    @Transactional
    public MerchantOnboardResponse onboardMerchant(MerchantOnboardRequest request) {
    	log.info("Starting merchant onboarding for business: {}", request.getBusinessName());
    	
        String merchantId = merchantIdGenerator.generate();
        log.info("Generated merchantId: {}", merchantId);
        
        MerchantEntity merchant = MerchantEntity.builder()
                .merchantId(merchantId)
                .businessName(request.getBusinessName())
                .email(request.getEmail())
                .environment(OnboardingConstants.DEFAULT_ENVIRONMENT)
                .build();
        log.info("Merchant entity created for merchantId {}: {}", merchantId, merchant);

        Instant createdAt = merchantRepository.save(merchant);
        log.info("Merchant saved to database at: {}", createdAt);
        
        String apiKey = apiKeyService.issueKeyFor(merchantId);
        
        log.info("API key issued for merchantId: {}", merchantId);

        return MerchantOnboardResponse.builder()
                .merchantId(merchantId)
                .apiKey(apiKey)
                .environment(OnboardingConstants.DEFAULT_ENVIRONMENT)
                .createdAt(createdAt)
                .build();
    }
}
