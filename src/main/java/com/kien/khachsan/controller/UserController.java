package com.kien.khachsan.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kien.khachsan.dto.auth.UserUpdateRequest;
import com.kien.khachsan.dto.user.UserResponse;
import com.kien.khachsan.entity.User;
import com.kien.khachsan.service.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = authService.getUserIdFromToken(token);
        User user = authService.getUserById(userId);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setFullname(user.getFullname());
        userResponse.setRole(user.getRole().getName());

        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UserUpdateRequest request) {

        String token = authHeader.substring(7);
        Long userId;
        try {
            userId = authService.getUserIdFromToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid token: " + e.getMessage()));
        }

        User user = authService.getUserById(userId);

        if (request.getCurrentPassword() == null || request.getCurrentPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Current password is required"));
        }

        if (!authService.verifyPassword(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Current password is incorrect"));
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }

        if (request.getFullname() != null && !request.getFullname().isEmpty()) {
            user.setFullname(request.getFullname());
        }

        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            if (request.getNewPassword().length() < 6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "New password must be at least 6 characters"));
            }
            authService.changePassword(userId, request.getCurrentPassword(), request.getNewPassword());
        } else {
            authService.updateUser(userId, user);
        }

        return ResponseEntity.ok(Map.of("message", "User updated successfully"));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Map<String, String>> deleteUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = authService.getUserIdFromToken(token);

        authService.deleteUser(userId);

        return ResponseEntity.ok(Map.of("message", "Account and related data deleted successfully"));
    }
}