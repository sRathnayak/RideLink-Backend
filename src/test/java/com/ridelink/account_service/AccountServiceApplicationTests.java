package com.ridelink.account_service;

import com.ridelink.account_service.config.JwtUtil;
import com.ridelink.account_service.dto.AuthResponse;
import com.ridelink.account_service.dto.LoginRequest;
import com.ridelink.account_service.model.User;
import com.ridelink.account_service.repository.UserRepository;
import com.ridelink.account_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setEmail("test@example.com");
        sampleUser.setPassword("encodedPassword");
        sampleUser.setFullName("Test User");
        sampleUser.setPhoneNumber("+94771234567");
        sampleUser.setRole(User.Role.PASSENGER);
    }

    @Test
    @DisplayName("Register user - success")
    void testRegisterUser_success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        User result = userService.registerUser(sampleUser);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Register user - duplicate email throws exception")
    void testRegisterUser_duplicateEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.registerUser(sampleUser))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("already registered");
    }

    @Test
    @DisplayName("Login user - success returns JWT")
    void testLoginUser_success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("rawPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("jwt-token");

        AuthResponse response = userService.loginUser(loginRequest);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getRole()).isEqualTo("PASSENGER");
    }

    @Test
    @DisplayName("Login user - wrong password throws exception")
    void testLoginUser_wrongPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrongPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> userService.loginUser(loginRequest))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Invalid email or password");
    }

    @Test
    @DisplayName("Login user - email not found throws exception")
    void testLoginUser_emailNotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("unknown@example.com");
        loginRequest.setPassword("pass");

        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.loginUser(loginRequest))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Invalid email or password");
    }

    @Test
    @DisplayName("Get all users - returns list")
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));
        List<User> users = userService.getAllUsers();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("Get user by ID - found")
    void testGetUserById_found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        User result = userService.getUserById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Get user by ID - not found throws exception")
    void testGetUserById_notFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserById(99L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("not found with id");
    }

    @Test
    @DisplayName("JwtUtil - generate, validate, and extract claims")
    void testJwtUtil_fullFlow() {
        JwtUtil jwtUtilReal = new JwtUtil();
        String token = jwtUtilReal.generateToken("admin@ridelink.com", "ADMIN");
        assertThat(token).isNotNull().isNotBlank();
        assertThat(jwtUtilReal.validateToken(token)).isTrue();
        assertThat(jwtUtilReal.extractEmail(token)).isEqualTo("admin@ridelink.com");
        assertThat(jwtUtilReal.extractRole(token)).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("JwtUtil - invalid token returns false")
    void testJwtUtil_invalidToken() {
        JwtUtil jwtUtilReal = new JwtUtil();
        assertThat(jwtUtilReal.validateToken("not.a.valid.token")).isFalse();
    }
}
