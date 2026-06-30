package com.kien.khachsan.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kien.khachsan.entity.Role;
import com.kien.khachsan.entity.User;
import com.kien.khachsan.repository.RoleRepository;
import com.kien.khachsan.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(RoleRepository roleRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Kiểm tra xem đã có user nào có role_id = 1 (ADMIN) chưa
        boolean adminExists = userRepository.existsByRoleId(1L);

        if (!adminExists) {
            // Lấy role ADMIN từ database
            Role adminRole = roleRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setEmail("admin@example.com");
            admin.setFullname("Administrator");
            admin.setRole(adminRole);  // ← Set Role object
            userRepository.save(admin);
            System.out.println("✅ Admin created with role: " + adminRole.getName());
        } else {
            System.out.println("✅ Admin already exists, skipping creation.");
        }
    }
}