package com.MallareddyCollegeOfTechnology.app.Repositories;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t.classTeacherOf FROM Teacher t WHERE t.id = :teacherId")
    Section findClassTeacherSectionByTeacherId(@Param("teacherId") Long teacherId);
    Teacher findByClassTeacherOf(Section section);
    List<Teacher> findBySubjectSectionsContains(Section section);
}
