package com.MallareddyCollegeOfTechnology.app.Repositories;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Subject;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByTeacher(Teacher teacher);
    List<Subject> findBySection(Section section);
    Subject findByNameAndSection(String name, Section section);
}
