package com.MallareddyCollegeOfTechnology.app.Controllers.ClassController.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetClassDTO {

    private String sectionName;
    private String classTeacherName;
    private List<StudentDTO> StudentDTOList;
    private List<SubjectDTO> subjectDTOList;
    private String events;
    private String timetableImageBase64;
}
