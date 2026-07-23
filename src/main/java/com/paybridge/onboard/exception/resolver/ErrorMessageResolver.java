package com.paybridge.onboard.exception.resolver;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.paybridge.onboard.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ErrorMessageResolver {

    private final MessageSource messageSource;

    public String resolve(ErrorCode errorCode) {
        return messageSource.getMessage(String.valueOf(errorCode.getCode()), null, null);
    }
}
