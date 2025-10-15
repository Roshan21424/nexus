package com.MallareddyCollegeOfTechnology.app.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //id of the user
    private String name; //name of the user
    private String email; //email of the user

    @Enumerated(EnumType.STRING)
    private Role role;  //role of the user
    public enum Role {
        STUDENT,
        TEACHER
    }


    private String password;

    private String profileDescription;


    private boolean active;


    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;

    private String twoFactorSecret;
    private boolean isTwoFactorEnabled = false;
    private String signUpMethod;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;
}