package com.academy.mars.service;

import com.academy.mars.entity.Admin;
import com.academy.mars.entity.User;
import com.academy.mars.repository.AdminRepository;

import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void registerAdmin(User user) {
        Admin admin = new Admin();
        admin.setUser(user);
        adminRepository.save(admin);
    }
}
