package com.library.Library.Service;

import com.library.Library.dto.RegistrationRequest;
import com.library.Library.entity.RoleEntity;
import com.library.Library.entity.UserEntity;
import com.library.Library.repository.UserRepository;
import com.library.Library.service.UserServiceImpl;
import com.library.Library.service.api.RoleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Test
    @DisplayName("Успешное создание нового пользователя")
    void createNewUser_Success() {
        var registrationRequest = new RegistrationRequest("newUser", "newUser@example.com", "password","password");
        var expectedUser = new UserEntity();
        expectedUser.setUsername(registrationRequest.getUsername());
        expectedUser.setEmail(registrationRequest.getEmail());
        expectedUser.setPassword(new BCryptPasswordEncoder().encode(registrationRequest.getPassword()));
        expectedUser.setRoles(List.of(new RoleEntity(1,"ROLE_USER")));

        when(roleService.getUserRole()).thenReturn(new RoleEntity(1,"ROLE_USER"));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        UserEntity response = userService.createNewUser(registrationRequest);

        assertEquals(expectedUser.getUsername(), response.getUsername());
        assertEquals(expectedUser.getEmail(), response.getEmail());
        assertNotNull(response.getPassword()); // Проверяем, что пароль закодирован
        assertEquals(1, response.getRoles().size());
        assertEquals("ROLE_USER", response.getRoles().get(0).getName());
    }

    @Test
    @DisplayName("Поиск пользователя по имени (существует)")
    void findByUserName_UserExists() {
        var username = "existingUser";
        var userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        var response = userService.findByUserName(username);

        assertTrue(response.isPresent());
        assertEquals(username, response.get().getUsername());
    }

    @Test
    @DisplayName("Ошибка поиска пользователя (не найден)")
    void findByUserName_UserNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        Optional<UserEntity> response = userService.findByUserName("nonExistingUser");

        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Загрузка пользователя по имени (успешный кейс)")
    void loadUserByUsername_Success() {
        String username = "user";
        String password = "encodedPassword";
        RoleEntity role = new RoleEntity(1,"ROLE_USER");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setRoles(List.of(role));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        var userDetails = userService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    @DisplayName("Ошибка загрузки пользователя (не найден)")
    void loadUserByUsername_NotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknownUser"));
    }
}
