package com.MallareddyCollegeOfTechnology.app.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., Mathematics, Physics, etc.

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher; // taught by one teacher

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section; // belongs to one section
}
