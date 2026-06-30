package com.kien.khachsan.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kien.khachsan.dto.auth.SignupRequest;
import com.kien.khachsan.entity.Role;
import com.kien.khachsan.entity.User;
import com.kien.khachsan.repository.RoleRepository;
import com.kien.khachsan.utils.JwtUtils;

@Service
@Transactional
public class AuthService {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService,
                       RoleRepository roleRepository,
                       JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String username, String password) {
        User user = userService.getUserByEmailOrUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username/email or password");
        }
        return user;
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User registerUser(SignupRequest request) {
        // Lấy role USER từ database
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname() != null ? request.getFullname() : "");
        user.setRole(userRole);

        return userService.registerUser(user, request.getPassword());
    }

    public String generateAccessToken(User user) {
        return jwtUtils.generateAccessToken(user);
    }

    public String generateRefreshToken(Long userId) {
        return jwtUtils.generateRefreshToken(userId);
    }

    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    public Long getUserIdFromToken(String token) {
        return jwtUtils.getUserIdFromToken(token);
    }

    public User getUserById(Long id) {
        return userService.getUserById(id);
    }

    public void updateUser(Long id, User user) {
        userService.updateUser(id, user);
    }

    public void changePassword(Long id, String currentPassword, String newPassword) {
        userService.changePassword(id, currentPassword, newPassword);
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}