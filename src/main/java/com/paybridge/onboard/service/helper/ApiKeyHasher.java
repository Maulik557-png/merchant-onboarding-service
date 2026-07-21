package com.paybridge.onboard.service.helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import org.springframework.stereotype.Component;

@Component
public class ApiKeyHasher {

    private static final String ALGORITHM = "SHA-256";

    public String hash(String rawKey) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hashBytes = digest.digest(rawKey.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is mandatory in every JDK per the Java Cryptography spec —
            // this branch is unreachable in practice. This is a checked-to-unchecked
            // conversion, not the "service layer swallowing an infra exception"
            // pattern we avoid elsewhere — there's no meaningful recovery path here,
            // so there's nothing for GlobalExceptionHandler to do with it either.
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
    
}