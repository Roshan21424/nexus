package com.MallareddyCollegeOfTechnology.app.Entities;

import jakarta.persistence.*;
import lombok.*;

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
}