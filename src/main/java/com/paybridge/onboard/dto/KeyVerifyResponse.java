package com.paybridge.onboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeyVerifyResponse {
    private boolean valid;
    private String merchantId;
    private String status;
}
