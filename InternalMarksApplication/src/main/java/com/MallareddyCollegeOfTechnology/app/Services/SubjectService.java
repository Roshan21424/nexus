package com.MallareddyCollegeOfTechnology.app.Services;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Subject;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import com.MallareddyCollegeOfTechnology.app.Repositories.SectionRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.SubjectRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SectionRepository sectionRepository;

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<Subject> getSubjectsByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        return teacher != null ? subjectRepository.findByTeacher(teacher) : List.of();
    }

    public List<Subject> getSubjectsBySectionEnum(Section.SectionEnum sectionEnum) {
        Section section = sectionRepository.findBySectionEnum(sectionEnum);
        return section != null ? subjectRepository.findBySection(section) : List.of();
    }


    public Subject getSubjectByNameInSection(String name, Section.SectionEnum sectionEnum) {
        Section section = sectionRepository.findBySectionEnum(sectionEnum);
        return section != null ? subjectRepository.findByNameAndSection(name, section) : null;
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}
