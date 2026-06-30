package com.kien.khachsan.repository;

import com.kien.khachsan.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findBySoDienThoai(String soDienThoai);
    Optional<Customer> findByUser_Id(Long userId);
}