package com.library.Library.controller;


import com.library.Library.dto.JwtRequest;
import com.library.Library.dto.JwtResponse;
import com.library.Library.dto.RegistrationRequest;
import com.library.Library.exceptions.AppError;
import com.library.Library.security.JwtTokenUtils;
import com.library.Library.service.api.AuthService;
import com.library.Library.service.api.UserService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthService authService;
    @Test
    @DisplayName("Авторизация успешное создание токена")
    void createAuthToken_Success() {
        //given
        var authRequest = new JwtRequest("user", "password");
        var userDetails = new User("user", "password", new ArrayList<>());
        String token = "mockedToken";

        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtTokenUtils.generateToken(any())).thenReturn(token);

        var response = authController.createAuthToken(authRequest);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, ((JwtResponse) response.getBody()).getToken());
    }

    @Test
    @DisplayName("Авторизация не успешное создание токена")
    void createAuthToken_BadCredentials() {
        var authRequest = new JwtRequest("user", "usss");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any());

        var response = authController.createAuthToken(authRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertInstanceOf(AppError.class, response.getBody());
        assertEquals("не правильный логин или пароль", ((AppError) response.getBody()).getMessage());
    }

    @Test
    @DisplayName("Регистрация успешное создание пользователя")
    void createNewUser_Success() {
        var registrationRequest = new RegistrationRequest("newUser", "hfhfhf@fkfkf","password","password");
        var expectedResponse = ResponseEntity.ok("пользователь создан");

        when(authService.createNewUser(any())).thenAnswer(invocation -> ResponseEntity.ok("пользователь создан"));

        var response = authController.createNewUser(registrationRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("пользователь создан", response.getBody());
    }

}
