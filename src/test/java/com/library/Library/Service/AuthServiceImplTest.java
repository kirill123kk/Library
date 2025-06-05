package com.library.Library.Service;

import com.library.Library.dto.JwtRequest;
import com.library.Library.dto.JwtResponse;
import com.library.Library.dto.RegistrationRequest;
import com.library.Library.entity.UserEntity;
import com.library.Library.exceptions.AppError;
import com.library.Library.security.JwtTokenUtils;
import com.library.Library.service.AuthServiceImpl;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("Успешная авторизация пользователя")
    void createAuthToken_Success() {
        var authRequest = new JwtRequest("user", "password");
        var userDetails = new User("user", "password", new ArrayList<>());
        String token = "mockedToken";
        var authenticationMock = mock(Authentication.class);
        doReturn(authenticationMock)
                .when(authenticationManager).authenticate(any());

        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtTokenUtils.generateToken(any())).thenReturn(token);

        var response = authService.createAuthToken(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, ((JwtResponse) response.getBody()).getToken());
    }

    @Test
    @DisplayName("Ошибка авторизации (неправильные данные)")
    void createAuthToken_BadCredentials() {
        JwtRequest authRequest = new JwtRequest("user", "wrongpassword");

        doThrow(new BadCredentialsException("Неправильный логин или пароль"))
                .when(authenticationManager).authenticate(any());

        ResponseEntity<?> response = authService.createAuthToken(authRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertInstanceOf(AppError.class, response.getBody());
        assertEquals("Неправильный логин или пароль", ((AppError) response.getBody()).getMessage());
    }

    @Test
    @DisplayName("Ошибка регистрации не совпадают пароли")
    void createNewUser_PasswordMismatch() {
        var registrationRequest = new RegistrationRequest("user", "adsf@dasf","password", "sadfd");

        var response = authService.createNewUser(registrationRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertInstanceOf(AppError.class, response.getBody());
        assertEquals("Пароли не совпадают", ((AppError) response.getBody()).getMessage());
    }

    @Test
    @DisplayName("Ошибка регистрации ")
    void createNewUser_UserAlreadyExists() {
        RegistrationRequest registrationRequest = new RegistrationRequest("user", "adsf@dasf","password", "password");

        when(userService.findByUserName(anyString())).thenReturn(Optional.of(new UserEntity()));

        ResponseEntity<?> response = authService.createNewUser(registrationRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertInstanceOf(AppError.class, response.getBody());
        assertEquals("Пользователь с указанным именем уже существует", ((AppError) response.getBody()).getMessage());
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    void createNewUser_Success() {
        RegistrationRequest registrationRequest = new RegistrationRequest("user", "adsf@dasf","password", "password");
        UserEntity newUser = new UserEntity();

        when(userService.findByUserName(anyString())).thenReturn(Optional.empty());
        when(userService.createNewUser(any())).thenReturn(newUser);

        ResponseEntity<?> response = authService.createNewUser(registrationRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newUser, response.getBody());
    }

}
