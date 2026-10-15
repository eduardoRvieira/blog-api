package com.api.blog_api.exception;

import java.time.LocalDateTime;

public record StandardError(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message
) {
}