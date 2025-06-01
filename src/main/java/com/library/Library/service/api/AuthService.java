package com.library.Library.service.api;

import com.library.Library.dto.JwtRequest;
import com.library.Library.dto.RegistrationRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest);

    ResponseEntity<?> createNewUser(@RequestBody RegistrationRequest registrationUserDto);
}
