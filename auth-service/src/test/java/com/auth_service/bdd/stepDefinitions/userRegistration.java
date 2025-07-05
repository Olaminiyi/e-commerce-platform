package com.auth_service.bdd.stepDefinitions;

import com.auth_service.auth.AuthenticationResponse;
import com.auth_service.auth.AuthenticationService;
import com.auth_service.auth.RegisterRequest;
import com.auth_service.config.JwtService;
import com.auth_service.user.Role;
import com.auth_service.user.User;
import com.auth_service.user.UserRepository;
import io.cucumber.java.en.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class userRegistration {
    @Mock
    private UserRepository repository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private User mockUser;
    @Mock
    private RegisterRequest request;
    @Mock
    private AuthenticationResponse response;
    @InjectMocks
    private AuthenticationService authService;
    String token;


    @Given("a new user enters his details")
    public void a_new_user_enters_his_details() {
         request = new RegisterRequest("paul","labubu","paul@gmail.com", "labubu001");
         mockUser = User.builder()
                .firstName("paul")
                .lastName("labubu")
                .password("labubu001")
                .email("paul@gmail.com")
                .role(Role.USER)
                .build();
     //   when(jwtService.generateToken(mockUser)).thenReturn("mock-jwt-token");
      //  lenient().when(passwordEncoder.encode("labubu001")).thenReturn("labubu001");
     //   System.out.println("working");
    }

    @Given("the user register with theses details")
    public void the_user_register_with_theses_details() {
        response = authService.register(request);
        System.out.println("working");
    }

    @Then("a token should be generated")
    public void a_token_should_be_generated() {
      //  verify(repository).save(mockUser);
     //   verify(jwtService).generateToken(mockUser);
         token = jwtService.generateToken(mockUser);

        assertThat(token).isNotNull();
       // assertThat(response.getToken()).isEqualTo("mock-jwt-token");
        System.out.println("working");
    }

    @Then("username should be {string}")
    public void username_should_be(String username) {
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(username, extractedUsername);
    }
}
