package com.auth_service.unit;

import com.auth_service.auth.AuthenticateRequest;
import com.auth_service.auth.AuthenticationResponse;
import com.auth_service.auth.AuthenticationService;
import com.auth_service.auth.RegisterRequest;
import com.auth_service.config.JwtService;
import com.auth_service.user.Role;
import com.auth_service.user.User;
import com.auth_service.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        RegisterRequest request = new RegisterRequest("Lola","Idije","idije@gmail.com", passwordEncoder.encode("idije001"));
        User mockUser = User.builder()
                .firstName("Lola")
                .lastName("Idije")
                .password(passwordEncoder.encode("idije001"))
                .email("idije@gmail.com")
                .role(Role.USER)
                .build();

        when(jwtService.generateToken(mockUser)).thenReturn("mock-jwt-token");

        AuthenticationResponse response = authService.register(request);

        verify(repository).save(mockUser);
        verify(jwtService).generateToken(mockUser);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mock-jwt-token");
    }

    @Test
    void shouldAuthenticateAndReturnJwtToken(){
        AuthenticateRequest request = new AuthenticateRequest("johnbull@gmail.com", passwordEncoder.encode("johnbull001"));
        User mockUser = User.builder()
                .firstName("John")
                .lastName("Bull")
                .password(passwordEncoder.encode("johnbull001"))
                .email("johnbull@gmail.com")
                .role(Role.USER)
                .build();

        when(jwtService.generateToken(mockUser)).thenReturn("mock-jwt-token");
        when(repository.findByEmail("johnbull@gmail.com")).thenReturn(Optional.of(mockUser));

        AuthenticationResponse response = authService.authenticate(request);

        verify(authenticationManager).authenticate(any());
        verify(repository).findByEmail("johnbull@gmail.com");
        verify(jwtService).generateToken(mockUser);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mock-jwt-token");
    }

}
