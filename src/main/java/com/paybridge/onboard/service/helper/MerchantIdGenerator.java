package com.paybridge.onboard.service.helper;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MerchantIdGenerator {

    public String generate() {
        String uuidNoDashes = UUID.randomUUID().toString().replace("-", "");
        return "mch_" + uuidNoDashes.substring(0, 12);
    }
}
