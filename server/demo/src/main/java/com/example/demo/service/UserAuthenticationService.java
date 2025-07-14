package com.example.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.request.UserSignRequest;
import com.example.demo.dto.response.TokenResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.DuplicateEntryException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.UnexpectedException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public TokenResponse signIn(HttpServletResponse response, UserSignRequest userSignRequest) {
        UserEntity userEntity = userRepository.findByEmail(userSignRequest.getEmail());
        if (userEntity == null || !passwordEncoder.matches(userSignRequest.getPassword(), userEntity.getPassword())) {
            throw new InvalidRequestException("Invalid email or password.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userSignRequest.getEmail(), userSignRequest.getPassword()));

        String accessToken = jwtAuthenticationService.generateToken(userEntity.getEmail());
        String refreshToken = jwtAuthenticationService.generateToken(userEntity.getEmail());
        jwtAuthenticationService.setRefreshTokenCookie(response, refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public TokenResponse signUp(HttpServletResponse response, UserSignRequest userSignRequest) {

        UserSignRequest userSignRequestForSignIn = UserSignRequest.builder()
                .email(userSignRequest.getEmail())
                .password(userSignRequest.getPassword())
                .build();

        if (userRepository.existsByEmail(userSignRequest.getEmail())) {
            throw new DuplicateEntryException("User already exists.");
        }

        userSignRequest.setPassword(passwordEncoder.encode(userSignRequest.getPassword()));
        UserEntity userEntity = userMapper.UserSignRequestToUserEntity(userSignRequest);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (Exception e) {
            throw new UnexpectedException("Failed to save user.", e);
        }

        return signIn(response, userSignRequestForSignIn);
    }

    public void signOut(HttpServletResponse response, String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new InvalidRequestException("Refresh token is required.");
        }

        String username = jwtAuthenticationService.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (refreshToken == null || !jwtAuthenticationService.isTokenValid(refreshToken, userDetails)) {
            throw new InvalidRequestException("Invalid refresh token.");
        }

        jwtAuthenticationService.clearRefreshTokenCookie(response);
    }

    public TokenResponse refreshToken(HttpServletResponse response, String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new InvalidRequestException("Refresh token is required.");
        }

        String username = jwtAuthenticationService.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (refreshToken == null || !jwtAuthenticationService.isTokenValid(refreshToken, userDetails)) {
            throw new InvalidRequestException("Invalid refresh token.");
        }

        String newAccessToken = jwtAuthenticationService.generateToken(username);
        String newRefreshToken = jwtAuthenticationService.generateToken(username);
        jwtAuthenticationService.setRefreshTokenCookie(response, newRefreshToken);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

}
