package com.paybridge.onboard.exception;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int errorCode;
    private String message;
    private String traceId;
    private Instant timestamp;
    private String path;
    private boolean retryable;
    private Object details;
}