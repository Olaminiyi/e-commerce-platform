package com.auth_service.unit;
import com.auth_service.config.JwtService;
import com.auth_service.exception.model.JwtException;
import com.auth_service.user.Role;
import com.auth_service.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    private JwtService jwtService;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        userDetails = User.builder()
                .email("james@example.com")
                .password("password")
                .role(Role.USER)
                .firstName("James")
                .lastName("Doe")
                .build();
    }

    @Test
    void testGenerateTokenAndExtractUsername() {
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(userDetails.getUsername(), extractedUsername);
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testIsTokenInvalidWithWrongUsername() {
        String token = jwtService.generateToken(userDetails);

        UserDetails otherUser = User.builder()
                .email("other@example.com")
                .password("password")
                .role(Role.USER)
                .firstName("Other")
                .lastName("User")
                .build();

        boolean isValid = jwtService.isTokenValid(token, otherUser);
        assertFalse(isValid);
    }

    @Test
    void testTokenIsExpired() throws InterruptedException {

        Map<String, Object> extraClaims = new HashMap<>();
        String token = Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000)) // 1 sec
                .signWith(Keys.hmacShaKeyFor("cd08700adb9d70cb9313a051aa52c15a2c7d1fa24f49e9dbf94ca33b5c67b9a3"
                        .getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
                .compact();

        Thread.sleep(1500);

        JwtException exception = assertThrows(JwtException.class, () ->
                jwtService.isTokenValid(token, userDetails));

        assertEquals("invalid or expired token", exception.getMessage());
        assertEquals(401, exception.getStatus());
    }

}
