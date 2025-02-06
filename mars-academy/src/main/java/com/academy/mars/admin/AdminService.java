package com.academy.mars.admin;

import com.academy.mars.user.User;
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
