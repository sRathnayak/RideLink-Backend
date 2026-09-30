package com.ridelink.account_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "JWT authentication response returned after successful login")
public class AuthResponse {

    @Schema(description = "Signed JWT Bearer token")
    private String token;

    @Schema(description = "Authenticated user's email")
    private String email;

    @Schema(description = "Authenticated user's role (PASSENGER | DRIVER | ADMIN)")
    private String role;
}