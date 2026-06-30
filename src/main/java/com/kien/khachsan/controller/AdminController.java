package com.kien.khachsan.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kien.khachsan.dto.user.UserSummaryDTO;
import com.kien.khachsan.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // ===== USER MANAGEMENT =====
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/users/{id}/role")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Map<String, String>> updateUserRole(
            @PathVariable Long id,
            @RequestParam String roleName) {
        userService.updateUserRole(id, roleName);
        return ResponseEntity.ok(Map.of(
            "message", "Role updated successfully",
            "user_id", String.valueOf(id),
            "new_role", roleName
        ));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }

    // ===== PERMISSION MANAGEMENT =====
    @PostMapping("/users/{id}/permissions")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Map<String, String>> addPermissionToUser(
            @PathVariable Long id,
            @RequestParam String permissionName) {
        userService.addPermissionToUser(id, permissionName);
        return ResponseEntity.ok(Map.of(
            "message", "Permission added successfully",
            "user_id", String.valueOf(id),
            "permission", permissionName
        ));
    }

    @DeleteMapping("/users/{id}/permissions")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Map<String, String>> removePermissionFromUser(
            @PathVariable Long id,
            @RequestParam String permissionName) {
        userService.removePermissionFromUser(id, permissionName);
        return ResponseEntity.ok(Map.of(
            "message", "Permission removed successfully",
            "user_id", String.valueOf(id),
            "permission", permissionName
        ));
    }

    // ===== TEST =====
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<Map<String, String>> adminTest() {
        return ResponseEntity.ok(Map.of("message", "You have permission!"));
    }
}