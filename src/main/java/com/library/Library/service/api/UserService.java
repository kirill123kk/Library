package com.library.Library.service.api;

import com.library.Library.dto.RegistrationRequest;
import com.library.Library.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserEntity createNewUser(RegistrationRequest registrationUserDto);

    Optional<UserEntity> findByUserName(String login);
}
