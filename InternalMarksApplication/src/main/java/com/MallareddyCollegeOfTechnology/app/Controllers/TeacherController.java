package com.MallareddyCollegeOfTechnology.app.Controllers;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import com.MallareddyCollegeOfTechnology.app.Services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/class-teacher/{sectionEnum}")
    public Teacher getClassTeacherOfSection(@PathVariable Section.SectionEnum sectionEnum) {
        return teacherService.getClassTeacherOfSection(sectionEnum);
    }

    @PutMapping("/assign_class_teacher_to_section")
    public String assignClassTeacher(@RequestParam Long teacherId, @RequestParam Long sectionId) {
         teacherService.assignClassTeacherToSection(teacherId, sectionId);
         return "success";
    }

    @GetMapping("/subject-section/{sectionEnum}")
    public List<Teacher> getTeachersBySubjectSection(@PathVariable Section.SectionEnum sectionEnum) {
        return teacherService.getTeachersBySubjectSection(sectionEnum);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }
}
