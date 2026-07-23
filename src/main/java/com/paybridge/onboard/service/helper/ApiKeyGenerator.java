package com.paybridge.onboard.service.helper;

import java.security.SecureRandom;
import java.util.HexFormat;

import org.springframework.stereotype.Component;

import com.paybridge.onboard.constants.OnboardingConstants;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiKeyGenerator {

    // SecureRandom, not Random — Random is seeded predictably enough to be
    // unsuitable for anything security-sensitive. This is non-negotiable for a credential.
    private final SecureRandom secureRandom;

    public String generate() {
        byte[] randomBytes = new byte[OnboardingConstants.RAW_KEY_BYTE_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return OnboardingConstants.SANDBOX_KEY_PREFIX + HexFormat.of().formatHex(randomBytes);
    }
}