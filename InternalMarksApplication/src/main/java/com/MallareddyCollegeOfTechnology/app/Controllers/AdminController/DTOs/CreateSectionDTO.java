package com.MallareddyCollegeOfTechnology.app.Controllers.AdminController.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSectionDTO {
    private Long teacherId;
    private List<Long> studentsIds;
    private List<Long> subjectIds;
 }
