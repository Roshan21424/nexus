package com.MallareddyCollegeOfTechnology.app.Repositories;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Section findBySectionEnum(Section.SectionEnum sectionEnum);
}
