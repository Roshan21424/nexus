package com.MallareddyCollegeOfTechnology.app.Repositories;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
//    List<Student> findBySection_SectionEnum(Enum<?> sectionEnum);
    List<Student> findByStudentRole(Student.StudentRole role);
}
