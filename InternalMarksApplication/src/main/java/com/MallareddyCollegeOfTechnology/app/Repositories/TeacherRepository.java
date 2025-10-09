package com.MallareddyCollegeOfTechnology.app.Repositories;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByClassTeacherOf(Section section);
    List<Teacher> findBySubjectSectionsContains(Section section);
}
