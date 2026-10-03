package com.ridelink.account_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User account entity")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique user ID", example = "1")
    private Long id;

    @Email(message = "Email must be a valid address")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    @Schema(description = "User email address", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    @Schema(description = "User password (will be BCrypt-encrypted)", example = "secret123")
    private String password;

    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    @Schema(description = "User full name", example = "John Doe")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Column(nullable = false)
    @Schema(description = "Phone number", example = "+94771234567")
    private String phoneNumber;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "User role", example = "PASSENGER")
    private Role role; // PASSENGER, DRIVER, ADMIN

    public enum Role {
        PASSENGER,
        DRIVER,
        ADMIN
    }
}