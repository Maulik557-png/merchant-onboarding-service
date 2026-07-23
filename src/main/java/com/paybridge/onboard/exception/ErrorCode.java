package com.paybridge.onboard.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	UNEXPECTED_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, false),
	DUPLICATE_MERCHANT_EMAIL(50001, HttpStatus.CONFLICT, false),
	DUPLICATE_API_KEY_HASH(50002, HttpStatus.CONFLICT, true),
	MISSING_API_KEY_HEADER(50003, HttpStatus.BAD_REQUEST, false),
	MALFORMED_REQUEST_BODY(50004, HttpStatus.BAD_REQUEST, false),
	VALIDATION_FAILED(50005, HttpStatus.BAD_REQUEST, false);

    private final int code;
    private final HttpStatus httpStatus;
    private final boolean retryable;

    ErrorCode(int code, HttpStatus httpStatus, boolean retryable) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.retryable = retryable;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public boolean isRetryable() {
        return retryable;
    }
}