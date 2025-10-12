package com.MallareddyCollegeOfTechnology.app.Controllers;

import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Student;
import com.MallareddyCollegeOfTechnology.app.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/assign_section_to_student")
    public String assignStudentSection(@RequestParam Long studentId, @RequestParam Long sectionId) {
       studentService.assignStudentToSection(studentId, sectionId);
       return "success";

    }

    @GetMapping("/section/{sectionEnum}")
    public List<Student> getStudentsBySection(@PathVariable Section.SectionEnum sectionEnum) {
        return studentService.getStudentsBySectionEnum(sectionEnum);
    }

    @GetMapping("/role/{role}")
    public List<Student> getStudentsByRole(@PathVariable Student.StudentRole role) {
        return studentService.getStudentsByRole(role);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
