package com.kien.khachsan.service;

import com.kien.khachsan.entity.Customer;
import com.kien.khachsan.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer getCustomerByUserId(Long userId) {
        return customerRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found for user: " + userId));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer existing = getCustomerById(id);
        if (customerDetails.getHoTen() != null) existing.setHoTen(customerDetails.getHoTen());
        if (customerDetails.getSoDienThoai() != null) existing.setSoDienThoai(customerDetails.getSoDienThoai());
        if (customerDetails.getEmail() != null) existing.setEmail(customerDetails.getEmail());
        if (customerDetails.getCccd() != null) existing.setCccd(customerDetails.getCccd());
        if (customerDetails.getDiaChi() != null) existing.setDiaChi(customerDetails.getDiaChi());
        if (customerDetails.getUser() != null) existing.setUser(customerDetails.getUser());
        return customerRepository.save(existing);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}