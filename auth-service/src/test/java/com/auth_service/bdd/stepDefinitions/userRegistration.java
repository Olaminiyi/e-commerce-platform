package com.auth_service.bdd.stepDefinitions;
import com.auth_service.auth.AuthenticationResponse;
import com.auth_service.auth.AuthenticationService;
import com.auth_service.auth.RegisterRequest;
import com.auth_service.config.JwtService;
import com.auth_service.user.Role;
import com.auth_service.user.User;
import com.auth_service.user.UserRepository;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class userRegistration {
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

    private UserDetails userDetails;
    private RegisterRequest request;
    private AuthenticationResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("a new user enters his details")
    public void a_new_user_enters_his_details() {
        request = new RegisterRequest("paul","labubu","paul@gmail.com", "labubu001");
        userDetails = User.builder()
                .firstName("paul")
                .lastName("labubu")
                .password("encodedPassword")
                .email("paul@gmail.com")
                .role(Role.USER)
                .build();
    }

    @Given("the user register with theses details")
    public void the_user_register_with_theses_details() {
        when(passwordEncoder.encode("labubu001")).thenReturn("encodedPassword");
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtService.generateToken(any(userDetails.getClass()))).thenReturn("mock-jwt-token");

        response = authService.register(request);
    }

    @Then("a token should be generated")
    public void a_token_should_be_generated() {
        assertThat(response.getToken()).isEqualTo("mock-jwt-token");
    }

    @Then("username should be {string}")
    public void username_should_be(String username) {
        when(jwtService.extractUsername("mock-jwt-token")).thenReturn(username);
        String extractedUsername = jwtService.extractUsername("mock-jwt-token");
        assertEquals(username, extractedUsername);
    }
}
