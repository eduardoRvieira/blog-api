package com.api.blog_api.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record PostResponseDto(
    UUID id,
    String autor,
    LocalDate data,
    String titulo,
    String text
) {

}
