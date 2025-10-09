package com.MallareddyCollegeOfTechnology.app.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

    @Enumerated(EnumType.STRING)
    private StudentRole studentRole; //role of the student

    @ManyToOne
    private Section section; //section of the student

    public enum StudentRole {
        GR, // Group Representative
        CR, // Class Representative
        NORMAL
    }
}
