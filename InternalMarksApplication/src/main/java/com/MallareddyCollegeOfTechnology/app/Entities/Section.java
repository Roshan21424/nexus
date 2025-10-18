package com.MallareddyCollegeOfTechnology.app.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class    Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //(column) id of the section

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private SectionEnum sectionEnum; //(column) section name

    @OneToOne(mappedBy = "classTeacherOf") //bi (not own)
    private Teacher classTeacher;//class teacher of the section

    @OneToMany //uni (own)
    @JoinColumn(name = "teacher_id")
    private Set<Student> students;//(column) students of the section


    @OneToMany(mappedBy = "section")//bi(not own)
    private Set<Subject> subjects; //subjects of the section

    @Lob
    @Column(columnDefinition = "TEXT")
    private String events; // store JSON string directly


    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String timetableImageBase64; // stores base64 image data

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String semesterScheduleImageBase64; // stores base64 image data

    public enum SectionEnum {
        CSE_2025_A,
        CSE_2025_B,
        ECE_2025_A,
        MECH_2025_A;
    }


    //will be removed later
    private String branchName;
    private int year;
    private String sectionLabel;
}
