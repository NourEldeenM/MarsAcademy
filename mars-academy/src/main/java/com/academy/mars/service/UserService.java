package com.academy.mars.service;

import com.academy.mars.entity.Notification;
import com.academy.mars.entity.*;
import com.academy.mars.repository.AdminRepository;
import com.academy.mars.repository.InstructorRepository;
import com.academy.mars.repository.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.academy.mars.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with ID %s not found";
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    public UserService(UserRepository userRepository, InstructorRepository instructorRepository, StudentRepository studentRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
        //delete explicitly
        switch (user.getRole()){
            case ROLE_STUDENT:
                studentRepository.delete(studentRepository.findById(user.getId()).get());
                break;
            case ROLE_ADMIN:
                adminRepository.delete(adminRepository.findById(user.getId()).get());
                break;
            case ROLE_INSTRUCTOR:
                instructorRepository.delete(instructorRepository.findById(user.getId()).get());
                break;
        }
        userRepository.delete(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, userId)));
    }

    public User createUser(User user) {
        User savedUser = userRepository.save(user);

        switch (savedUser.getRole()){
            case ROLE_STUDENT:
                Student student= new Student();
                student.setUser(savedUser);
                studentRepository.save(student);
                break;
            case ROLE_ADMIN:
                Admin admin= new Admin();
                admin.setUser(savedUser);
                adminRepository.save(admin);
                break;
            case ROLE_INSTRUCTOR:
                Instructor instructor = new Instructor();
                instructor.setUser(savedUser);
                //other remaining instructor fields
                instructor.setSpecialization(InstructorSpecialization.CS);
                instructorRepository.save(instructor);
                break;
        }
        return user;
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, userId)));
        existingUser.setId(updatedUser.getId());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }

    public void addNotification(User user, Notification notification) {
        user.addNotification(notification);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(Long.parseLong(id)) // Assuming `id` is a Long
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }
}
