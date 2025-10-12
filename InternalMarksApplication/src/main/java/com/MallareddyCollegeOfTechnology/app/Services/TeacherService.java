package com.MallareddyCollegeOfTechnology.app.Services;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import com.MallareddyCollegeOfTechnology.app.Repositories.SectionRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SectionRepository sectionRepository;

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public void assignClassTeacherToSection(Long teacherId, Long sectionId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        teacher.setClassTeacherOf(section);
        section.setClassTeacher(teacher);

        sectionRepository.save(section);

    }


    public Teacher getClassTeacherOfSection(Section.SectionEnum sectionEnum) {
        Section section = sectionRepository.findBySectionEnum(sectionEnum);
        return teacherRepository.findByClassTeacherOf(section);
    }

    public List<Teacher> getTeachersBySubjectSection(Section.SectionEnum sectionEnum) {
        Section section = sectionRepository.findBySectionEnum(sectionEnum);
        return teacherRepository.findBySubjectSectionsContains(section);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
