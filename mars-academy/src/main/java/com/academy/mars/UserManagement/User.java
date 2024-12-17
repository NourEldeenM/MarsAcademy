package com.academy.mars.UserManagement;//package com.academy.mars.UserManagement;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.experimental.SuperBuilder;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "users")
//@SuperBuilder
//@Inheritance(strategy = InheritanceType.JOINED)
//public class User {
//    @Id
//    @SequenceGenerator(
//            name = "user_sequence",
//            sequenceName = "user_sequence",
//            allocationSize = 1
//
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "user_sequence"
//    )
//    private Long id;
//    private String name;
//    @Column(nullable = false, unique = true)
//    private String email;
//    private String password;
//    private String profilePicture;
//    @Enumerated(EnumType.STRING)
//    protected AppUserRole role;
//    //    @Temporal(TemporalType.TIMESTAMP)
//    private LocalDateTime timeJoined;
//
//    public User() {
//
//    }
//
//    public User(Long id,
//                String name,
//                String email,
//                String password,
//                String profilePicture,
//                Role role,
//                LocalDateTime timeJoined) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.profilePicture = profilePicture;
//        this.role = role;
//        this.timeJoined = timeJoined;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", role=" + role +
//                '}';
//    }
//}

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User(String username, String email, String password, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
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
}

