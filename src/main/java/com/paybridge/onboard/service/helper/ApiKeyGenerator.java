package com.paybridge.onboard.service.helper;

import java.security.SecureRandom;
import java.util.HexFormat;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiKeyGenerator {

    // SecureRandom, not Random — Random is seeded predictably enough to be
    // unsuitable for anything security-sensitive. This is non-negotiable for a credential.
    private final SecureRandom secureRandom;

    public String generate() {
        byte[] randomBytes = new byte[32]; // 32 bytes = 256 bits
        secureRandom.nextBytes(randomBytes);
        return "mch_" + HexFormat.of().formatHex(randomBytes);
    }
}