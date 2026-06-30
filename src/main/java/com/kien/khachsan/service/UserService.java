package com.kien.khachsan.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kien.khachsan.dto.user.UserSummaryDTO;
import com.kien.khachsan.entity.Permission;
import com.kien.khachsan.entity.Role;
import com.kien.khachsan.entity.User;
import com.kien.khachsan.repository.PermissionRepository;
import com.kien.khachsan.repository.RoleRepository;
import com.kien.khachsan.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{6,}$"
    );

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PermissionRepository permissionRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== Lấy tất cả user (cho admin) =====
    public List<UserSummaryDTO> getAllUsers() {
    return userRepository.findAll().stream()
            .map(user -> new UserSummaryDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getFullname(),
                    user.getRole().getName(),
                    user.getCreatedAt()
            ))
            .collect(Collectors.toList());
}

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByEmailOrUsername(String login) {
        return userRepository.findByUsername(login)
                .or(() -> userRepository.findByEmail(login))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User registerUser(User user, String password) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new RuntimeException("Password must have at least 6 characters, 1 uppercase, 1 lowercase, 1 number, 1 special character");
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // ===== Cập nhật user (cho admin và user tự cập nhật) =====
    public User updateUser(Long id, User userDetails) {
        User existing = getUserById(id);

        if (userDetails.getEmail() != null) {
            existing.setEmail(userDetails.getEmail());
        }
        if (userDetails.getFullname() != null) {
            existing.setFullname(userDetails.getFullname());
        }
        if (userDetails.getRole() != null) {
            existing.setRole(userDetails.getRole());
        }

        return userRepository.save(existing);
    }

    public User changePassword(Long id, String currentPassword, String newPassword) {
        User user = getUserById(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
            throw new RuntimeException("Password must have at least 6 characters, 1 uppercase, 1 lowercase, 1 number, 1 special character");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    // ===== ADMIN MANAGEMENT (Thêm mới) =====
    public User updateUserRole(Long userId, String roleName) {
        User user = getUserById(userId);
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        user.setRole(role);
        return userRepository.save(user);
    }

    public User addPermissionToUser(Long userId, String permissionName) {
        User user = getUserById(userId);
        Permission permission = permissionRepository.findByName(permissionName)
                .orElseThrow(() -> new RuntimeException("Permission not found: " + permissionName));
        
        Role role = user.getRole();
        role.getPermissions().add(permission);
        roleRepository.save(role);
        
        return user;
    }

    public User removePermissionFromUser(Long userId, String permissionName) {
        User user = getUserById(userId);
        Permission permission = permissionRepository.findByName(permissionName)
                .orElseThrow(() -> new RuntimeException("Permission not found: " + permissionName));
        
        Role role = user.getRole();
        role.getPermissions().remove(permission);
        roleRepository.save(role);
        
        return user;
    }
}