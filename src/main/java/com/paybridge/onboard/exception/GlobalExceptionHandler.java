package com.paybridge.onboard.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.paybridge.onboard.exception.resolver.ErrorMessageResolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorMessageResolver errorMessageResolver;

    @ExceptionHandler(OnboardingException.class)
    public ResponseEntity<ErrorResponse> handleOnboardingException(
            OnboardingException ex, HttpServletRequest request) {
        return buildResponse(ex.getErrorCode(), ex.getDetails(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, HttpServletRequest request) {
        return buildResponse(resolveConstraintViolation(ex), null, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return buildResponse(ErrorCode.VALIDATION_FAILED, fieldErrors, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedBody(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        return buildResponse(ErrorCode.MALFORMED_REQUEST_BODY, null, request);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingApiKeyHeader(
            MissingRequestHeaderException ex, HttpServletRequest request) {
        return buildResponse(ErrorCode.MISSING_API_KEY_HEADER, null, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error", ex);
        return buildResponse(ErrorCode.UNEXPECTED_ERROR, null, request);
    }

    private ErrorCode resolveConstraintViolation(DataIntegrityViolationException ex) {
        String rootMessage = String.valueOf(ex.getMostSpecificCause().getMessage());
        if (rootMessage.contains("merchants_email_key")) {
            return ErrorCode.DUPLICATE_MERCHANT_EMAIL;
        }
        if (rootMessage.contains("api_keys_key_hash_key")) {
            return ErrorCode.DUPLICATE_API_KEY_HASH;
        }
        return ErrorCode.UNEXPECTED_ERROR;
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            ErrorCode errorCode, Object details, HttpServletRequest request) {
        ErrorResponse body = ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorMessageResolver.resolve(errorCode))
                .traceId(MDC.get("traceId"))
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .retryable(errorCode.isRetryable())
                .details(details)
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(body);
    }
}
