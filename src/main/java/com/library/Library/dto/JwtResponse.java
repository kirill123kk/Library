package com.library.Library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO ответа с JWT-токеном после успешной аутентификации")
public class JwtResponse {
    @Schema(description = "JWT-токен, используемый для аутентификации", example = "eyJhbGciOiJIUzI1...")
    private String token;
}
