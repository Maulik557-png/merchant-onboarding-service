package com.paybridge.onboard.entity;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiKeyEntity {
    private Long id;
    private String merchantId;
    private String keyHash;
    private String keyPrefix;
    private String status;
    private Instant createdAt;
    private Instant revokedAt;
}
