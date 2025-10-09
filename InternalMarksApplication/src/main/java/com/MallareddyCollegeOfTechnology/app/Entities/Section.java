package com.MallareddyCollegeOfTechnology.app.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private SectionEnum sectionEnum;

    private String branchName;
    private int year;
    private String sectionLabel; // e.g., A, B, C

    @OneToMany(mappedBy = "section")
    private Set<Student> students;

    @OneToOne(mappedBy = "classTeacherOf")
    private Teacher classTeacher;

    @OneToMany(mappedBy = "section")
    private Set<Subject> subjects;

    public enum SectionEnum {
        CSE_2025_A,
        CSE_2025_B,
        ECE_2025_A,
        MECH_2025_A
        // You can expand this enum with unique representations
    }
}
