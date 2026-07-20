package com.paybridge.onboard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MerchantOnboardRequest {

    @NotBlank(message = "businessName must not be blank")
    private String businessName;

    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    private String email;
}
