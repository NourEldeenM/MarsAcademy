package com.academy.mars.entity;

import com.academy.mars.NotificationsManagement.Notification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Use IDENTITY for Long id generation
    private Long id;  // Keep the id as Long
    @NotNull(message = "username can't be null")
    private String username;
    @NotNull(message = "email can't be null")
    private String email;
    @NotNull(message = "password can't be null")
    private String password;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "role can't be null")
    private UserRole role;

    public User(String username, String email, String password, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = userRole;
        notifications = new LinkedList<>();
    }

    @OneToMany
    @JoinTable(
            name = "user_notifications",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"),
            indexes = @Index(columnList = "student_id")

    )
    private List<Notification> notifications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }
}
