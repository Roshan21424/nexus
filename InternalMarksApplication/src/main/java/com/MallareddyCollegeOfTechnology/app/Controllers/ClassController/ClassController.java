package com.MallareddyCollegeOfTechnology.app.Controllers.ClassController;


import com.MallareddyCollegeOfTechnology.app.Controllers.ClassController.DTOs.GetClassDTO;
import com.MallareddyCollegeOfTechnology.app.Controllers.ClassController.DTOs.StudentDTO;
import com.MallareddyCollegeOfTechnology.app.Controllers.ClassController.DTOs.SubjectDTO;
import com.MallareddyCollegeOfTechnology.app.Entities.*;
import com.MallareddyCollegeOfTechnology.app.Repositories.SectionRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.StudentRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.TeacherRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.UserRepository;
import com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/get-class")
    public GetClassDTO getClassDetails(@RequestParam Long id) {

        //get the current user
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));

//        if (user.getRole() == User.Role.TEACHER) {

            //get the teacher
            Teacher teacher = teacherRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("teacher not found"));

            //get the section
            Section section = teacher.getClassTeacherOf();


            //create studentDTOs
            List<StudentDTO> studentDTOS = new ArrayList<>();
            for (Student student : section.getStudents()) {
                StudentDTO studentDTO = new StudentDTO(student.getId(), student.getName());
                studentDTOS.add(studentDTO);
            }

            //create subjectDTOs
            List<SubjectDTO> subjectDTOs = new ArrayList<>();
            for (Subject subject : section.getSubjects()) {
                SubjectDTO subjectDTO = new SubjectDTO(subject.getId(), subject.getName(), subject.getTeacher().getName());
                subjectDTOs.add(subjectDTO);
            }


            GetClassDTO getClassDTO = new GetClassDTO();
            getClassDTO.setSectionName(section.getSectionEnum().name());//setting the section name
            getClassDTO.setClassTeacherName(teacher.getName());//setting the teacher name
            getClassDTO.setStudentDTOList(studentDTOS);//setting the students
            getClassDTO.setSubjectDTOList(subjectDTOs);//setting the subjects
            getClassDTO.setEvents(section.getEvents());

            return getClassDTO;
//        }

    }
}
