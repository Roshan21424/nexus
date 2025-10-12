package com.MallareddyCollegeOfTechnology.app.Services;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Subject;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import com.MallareddyCollegeOfTechnology.app.Repositories.SectionRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.SubjectRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Section getSectionByEnum(Section.SectionEnum sectionEnum) {
        return sectionRepository.findBySectionEnum(sectionEnum);
    }

    // ---------- 5. Assign Subject, Teacher, and Section (All in One) ----------
    public Subject assignSubjectTeacherAndSection(String subjectName, Long teacherId, Long sectionId) {
        // Fetch teacher and section
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        // Create subject
        Subject subject = new Subject();
        subject.setName(subjectName); // assuming field is 'name'
        subject.setTeacher(teacher);
        subject.setSection(section);

        // Save the subject (owning side)
        return subjectRepository.save(subject);
    }

    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }
}
