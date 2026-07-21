package com.paybridge.onboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KeyVerifyRequest {

    @NotBlank(message = "apiKey must not be blank")
    private String apiKey;
}