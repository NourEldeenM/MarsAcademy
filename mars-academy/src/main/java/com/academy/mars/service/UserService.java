package com.academy.mars.service;

import com.academy.mars.entity.Instructor;
import com.academy.mars.entity.InstructorSpecialization;
import com.academy.mars.repository.InstructorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.academy.mars.entity.User;
import com.academy.mars.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with ID %s not found";
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;

    public UserService(UserRepository userRepository, InstructorRepository instructorRepository) {
        this.userRepository = userRepository;
        this.instructorRepository = instructorRepository;
    }

    public org.springframework.security.core.userdetails.User loadUserByUsername(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRole().getAuthorities()
        );
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
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
                break;
            case ROLE_ADMIN:
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
