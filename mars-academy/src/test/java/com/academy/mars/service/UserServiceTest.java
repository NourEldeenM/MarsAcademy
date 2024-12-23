package com.academy.mars.service;

import com.academy.mars.entity.*;
import com.academy.mars.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser_AsStudent() {
        User user = new User();
        user.setId(1L);
        user.setRole(UserRole.ROLE_STUDENT);

        Student student = new Student();
        student.setUser(user);

        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.createUser(user);

        assertNotNull(savedUser);
        assertEquals(UserRole.ROLE_STUDENT, savedUser.getRole());
        verify(userRepository, times(1)).save(user);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testCreateUser_AsAdmin() {
        User user = new User();
        user.setId(2L);
        user.setRole(UserRole.ROLE_ADMIN);

        Admin admin = new Admin();
        admin.setUser(user);

        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.createUser(user);

        assertNotNull(savedUser);
        assertEquals(UserRole.ROLE_ADMIN, savedUser.getRole());
        verify(userRepository, times(1)).save(user);
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void testCreateUser_AsInstructor() {
        User user = new User();
        user.setId(3L);
        user.setRole(UserRole.ROLE_INSTRUCTOR);

        Instructor instructor = new Instructor();
        instructor.setUser(user);

        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.createUser(user);

        assertNotNull(savedUser);
        assertEquals(UserRole.ROLE_INSTRUCTOR, savedUser.getRole());
        verify(userRepository, times(1)).save(user);
        verify(instructorRepository, times(1)).save(any(Instructor.class));
    }
}
