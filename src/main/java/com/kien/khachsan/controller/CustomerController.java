package com.kien.khachsan.controller;

import com.kien.khachsan.dto.customer.CustomerRequest;
import com.kien.khachsan.dto.customer.CustomerResponse;
import com.kien.khachsan.entity.Customer;
import com.kien.khachsan.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> responses = customerService.getAllCustomers().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToResponse(customerService.getCustomerById(id)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CustomerResponse> getCustomerByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(convertToResponse(customerService.getCustomerByUserId(userId)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('customer:write')")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        Customer customer = convertToEntity(request);
        Customer saved = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('customer:write')")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest request) {
        Customer customer = convertToEntity(request);
        Customer updated = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(convertToResponse(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('customer:delete')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    // ===== CONVERTERS =====
    private CustomerResponse convertToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getMaKH(),
                customer.getHoTen(),
                customer.getSoDienThoai(),
                customer.getEmail(),
                customer.getCccd(),
                customer.getDiaChi()
        );
    }

    private Customer convertToEntity(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setHoTen(request.getHoTen());
        customer.setSoDienThoai(request.getSoDienThoai());
        customer.setEmail(request.getEmail());
        customer.setCccd(request.getCccd());
        customer.setDiaChi(request.getDiaChi());
        return customer;
    }
}