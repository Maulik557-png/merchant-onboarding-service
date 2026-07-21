package com.paybridge.onboard.entity;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantEntity {
    private Long id;
    private String merchantId;
    private String businessName;
    private String email;
    private String environment;
    private Instant createdAt;
}