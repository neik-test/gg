package com.kien.khachsan.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kien.khachsan.entity.Permission;
import com.kien.khachsan.entity.Role;
import com.kien.khachsan.entity.User;
import com.kien.khachsan.service.UserService;
import com.kien.khachsan.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Long userId = jwtUtils.getUserIdFromToken(token);
                
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Load user từ database
                    User user = userService.getUserById(userId);
                    Role role = user.getRole();
                    
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    
                    // Thêm ROLE_ để dùng hasRole (nếu cần)
                    if (role != null) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                        
                        // Thêm tất cả permissions để dùng hasAuthority
                        for (Permission permission : role.getPermissions()) {
                            authorities.add(new SimpleGrantedAuthority(permission.getName()));
                        }
                    }
                    
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userId, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // token invalid → không set authentication
                System.err.println("JWT Authentication failed: " + e.getMessage());
            }
        }
        
        filterChain.doFilter(request, response);
    }
}