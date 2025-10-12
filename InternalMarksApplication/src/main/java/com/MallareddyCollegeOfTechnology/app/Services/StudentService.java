package com.MallareddyCollegeOfTechnology.app.Services;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Student;
import com.MallareddyCollegeOfTechnology.app.Repositories.SectionRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SectionRepository sectionRepository;

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getStudentsBySectionEnum(Section.SectionEnum sectionEnum) {
        Section section = sectionRepository.findBySectionEnum(sectionEnum);
        return studentRepository.findBySection(section);
    }

    public List<Student> getStudentsByRole(Student.StudentRole role) {
        return studentRepository.findByStudentRole(role);
    }

    public void assignStudentToSection(Long studentId, Long sectionId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        // Set owning side
        student.setSection(section);

        // Save only the owning side
        studentRepository.save(student);
    }


    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
