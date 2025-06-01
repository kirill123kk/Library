package com.library.Library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO запроса аутентификации (логина)")
public class JwtRequest {

    @Schema(description = "Имя пользователя", example = "john_doe")
    private String username;

    @Schema(description = "Пароль пользователя", example = "StrongPassword123!")
    private String password;
}
