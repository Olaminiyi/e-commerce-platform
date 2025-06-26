package com.auth_service.unit;

import com.auth_service.auth.AuthenticateRequest;
import com.auth_service.auth.AuthenticationResponse;
import com.auth_service.auth.AuthenticationService;
import com.auth_service.auth.RegisterRequest;
import com.auth_service.config.JwtService;
import com.auth_service.exception.model.AuthenticationException;
import com.auth_service.user.Role;
import com.auth_service.user.User;
import com.auth_service.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthenticationService authService;

    @Test
    void shouldRegisterAndReturnJwtToken(){
        RegisterRequest request = new RegisterRequest("Lola","Idije","idije@gmail.com", "idije001");
        User mockUser = User.builder()
                .firstName("Lola")
                .lastName("Idije")
                .password("idije001")
                .email("idije@gmail.com")
                .role(Role.USER)
                .build();

        when(jwtService.generateToken(mockUser)).thenReturn("mock-jwt-token");
        lenient().when(passwordEncoder.encode("idije001")).thenReturn("idije001");
        AuthenticationResponse response = authService.register(request);

        verify(repository).save(mockUser);
        verify(jwtService).generateToken(mockUser);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mock-jwt-token");
    }

    @Test
    void failToRegisterWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest(
                "John",
                "Doe",
                "john@example.com",
                "password123"
        );

        User existingUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("existing-password")
                .role(Role.USER)
                .build();

        when(repository.findByEmail("john@example.com")).thenReturn(Optional.of(existingUser));

        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> authService.register(request));

        assertEquals("email already in use", exception.getMessage());
        assertEquals(409, exception.getStatus());

        verify(repository).findByEmail("john@example.com");
        verify(repository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void shouldAuthenticateAndReturnJwtToken(){
        AuthenticateRequest request = new AuthenticateRequest("johnbull@gmail.com", "johnbull001");
        User mockUser = User.builder()
                .firstName("John")
                .lastName("Bull")
                .password("johnbull001")
                .email("johnbull@gmail.com")
                .role(Role.USER)
                .build();

        when(jwtService.generateToken(mockUser)).thenReturn("mock-jwt-token");
        when(repository.findByEmail("johnbull@gmail.com")).thenReturn(Optional.of(mockUser));
      //  lenient().when(passwordEncoder.encode("johnbull001")).thenReturn("johnbull001");

        AuthenticationResponse response = authService.authenticate(request);

        verify(authenticationManager).authenticate(any());
        verify(repository).findByEmail("johnbull@gmail.com");
        verify(jwtService).generateToken(mockUser);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mock-jwt-token");
    }

    @Test
    void failToAuthenticateAndReturnUserNotFound(){
        AuthenticateRequest request = new AuthenticateRequest("mark-t@gmail.com", passwordEncoder.encode("johnbull001"));

        when(repository.findByEmail("mark-t@gmail.com")).thenReturn(Optional.empty());

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> authService.authenticate(request));
        assertEquals("user not found", exception.getMessage());
        assertEquals(404, exception.getStatus());

    }

}
