package com.paybridge.onboard.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paybridge.onboard.constants.OnboardingConstants;
import com.paybridge.onboard.dto.KeyVerifyResponse;
import com.paybridge.onboard.entity.ApiKeyEntity;
import com.paybridge.onboard.repository.ApiKeyRepository;
import com.paybridge.onboard.service.helper.ApiKeyGenerator;
import com.paybridge.onboard.service.helper.ApiKeyHasher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyGenerator apiKeyGenerator;
    private final ApiKeyHasher apiKeyHasher;

    public String issueKeyFor(String merchantId) {
        String rawKey = apiKeyGenerator.generate();
        String keyHash = apiKeyHasher.hash(rawKey);
        String keyPrefix = rawKey.substring(0, OnboardingConstants.KEY_PREFIX_DISPLAY_LENGTH);

        ApiKeyEntity entity = ApiKeyEntity.builder()
                .merchantId(merchantId)
                .keyHash(keyHash)
                .keyPrefix(keyPrefix)
                .build();

        apiKeyRepository.save(entity);

        // Raw key exists only in this local variable and the caller's response.
        // It is never logged, never persisted, never returned by any other method.
        return rawKey;
    }

    public KeyVerifyResponse verifyKey(String rawApiKey) {
        String keyHash = apiKeyHasher.hash(rawApiKey);
        Optional<ApiKeyEntity> found = apiKeyRepository.findByKeyHash(keyHash);

        if (found.isEmpty()) {
            return KeyVerifyResponse.builder().valid(false).build();
        }

        ApiKeyEntity entity = found.get();
        boolean isActive = OnboardingConstants.STATUS_ACTIVE.equals(entity.getStatus());

        return KeyVerifyResponse.builder()
                .valid(isActive)
                .merchantId(isActive ? entity.getMerchantId() : null)
                .status(entity.getStatus())
                .build();
    }
}
