package com.MallareddyCollegeOfTechnology.app.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {

    @OneToOne
    private Section classTeacherOf; // Class teacher for one section

    @ManyToMany
    @JoinTable(
        name = "teacher_subject_sections",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    private Set<Section> subjectSections; // Sections where this teacher teaches subjects

    @OneToMany(mappedBy = "teacher")
    private Set<Subject> subjects;

}
