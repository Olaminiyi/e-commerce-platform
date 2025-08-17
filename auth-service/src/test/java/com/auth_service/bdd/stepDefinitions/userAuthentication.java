package com.auth_service.bdd.stepDefinitions;
import com.auth_service.auth.AuthenticateRequest;
import com.auth_service.auth.AuthenticationResponse;
import com.auth_service.auth.AuthenticationService;
import com.auth_service.config.JwtService;
import com.auth_service.user.Role;
import com.auth_service.user.User;
import com.auth_service.user.UserRepository;
import io.cucumber.java.en.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


public class userAuthentication {
    @Mock
    private UserRepository repository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthenticationService authService;

    private User userDetails;
    private AuthenticationResponse response;
    private AuthenticateRequest request;

    @io.cucumber.java.Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("a user enters his {string} and {string}")
    public void a_user_enters_his_and(String username, String password) {
        request = new AuthenticateRequest(username, password);
         userDetails = User.builder()
                .firstName("John")
                .lastName("Bull")
                .password("johnbull001")
                .email("johnbull@gmail.com")
                .role(Role.USER)
                .build();
        when(jwtService.generateToken(userDetails)).thenReturn("mock-jwt-token");
        when(repository.findByEmail(username)).thenReturn(Optional.of(userDetails));
    }

    @When("we authenticate with {string}")
    public void weAuthenticateWith(String username) {
        response = authService.authenticate(request);
        verify(authenticationManager).authenticate(any());
        verify(repository).findByEmail(username);
        verify(jwtService).generateToken(userDetails);
    }

    @Then("a token should be generated to validate registration")
    public void a_token_should_be_generated_to_validate_registration() {
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mock-jwt-token");
    }
}
