package com.kien.khachsan.dto.user;

import java.time.LocalDateTime;

public class UserSummaryDTO {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String role;
    private LocalDateTime createdAt;

    public UserSummaryDTO(Long id, String username, String email, String fullname, String role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullname() { return fullname; }
    public String getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}