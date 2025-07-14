package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.request.UserSignRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.TokenResponse;
import com.example.demo.service.UserAuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<TokenResponse>> signIn(
            HttpServletResponse response,
            @Valid @RequestBody UserSignRequest userSignInRequest) {
        return ApiResponse.success(
                "Sign in successfully",
                userAuthenticationService.signIn(response, userSignInRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<TokenResponse>> signUp(
            HttpServletResponse response,
            @Valid @RequestBody UserSignRequest userSignUpRequest) {
        return ApiResponse.success(
                "Sign up successfully",
                userAuthenticationService.signUp(response, userSignUpRequest));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse<Void>> signOut(
            HttpServletResponse response,
            @CookieValue(value = "auth_refresh_token") String refreshToken) {
        userAuthenticationService.signOut(response, refreshToken);
        return ApiResponse.success(
                "Sign out successfully");
    }

    @PostMapping("refresh-token")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            HttpServletResponse response,
            @CookieValue(value = "auth_refresh_token") String refreshToken) {
        return ApiResponse.success(
                "Token refreshed successfully",
                userAuthenticationService.refreshToken(response, refreshToken));
    }

    @GetMapping("not-protected")
    public String notProtectedExample() {
        return new String("This endpoint is not protected by security.");
    }

    @GetMapping("protected")
    public String protectedExample() {
        return new String("This endpoint is protected by security.");
    }

}
