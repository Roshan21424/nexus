package com.MallareddyCollegeOfTechnology.app.Controllers.ClassController.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long subjectID;
    private String subjectName;
    private String subjectTeacherName;
}
