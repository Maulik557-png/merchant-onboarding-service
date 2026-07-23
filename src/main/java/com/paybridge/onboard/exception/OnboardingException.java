package com.paybridge.onboard.exception;

import lombok.Getter;

@Getter
public class OnboardingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final ErrorCode errorCode;
    private final transient Object details;

    public OnboardingException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public OnboardingException(ErrorCode errorCode, Object details) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.details = details;
    }
}