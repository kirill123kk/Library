package com.library.Library.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@Schema(description = "DTO запроса для регистрации нового пользователя")
public class RegistrationRequest {
    @Schema(description = "Имя пользователя", example = "john_doe")
    private String username;

    @Schema(description = "Электронная почта пользователя", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Пароль пользователя", example = "StrongPassword123!")
    private String password;

    @Schema(description = "Подтверждение пароля", example = "StrongPassword123!")
    private String confirmPassword;

}