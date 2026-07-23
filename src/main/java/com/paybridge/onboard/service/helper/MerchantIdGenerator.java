package com.paybridge.onboard.service.helper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.paybridge.onboard.constants.OnboardingConstants;

@Component
public class MerchantIdGenerator {

    public String generate() {
        String uuidNoDashes = UUID.randomUUID().toString().replace("-", "");
        return OnboardingConstants.MERCHANT_ID_PREFIX + uuidNoDashes.substring(0, 12);
    }
}
