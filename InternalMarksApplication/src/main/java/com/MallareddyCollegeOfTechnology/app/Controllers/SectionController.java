package com.MallareddyCollegeOfTechnology.app.Controllers;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Services.SectionService;
import com.MallareddyCollegeOfTechnology.app.Services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
@CrossOrigin
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public Section createSection(@RequestBody Section section) {
        return sectionService.saveSection(section);
    }

    @GetMapping
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    @GetMapping("/{sectionEnum}")
    public Section getSectionByEnum(@PathVariable Section.SectionEnum sectionEnum) {
        return sectionService.getSectionByEnum(sectionEnum);
    }

    @PutMapping("/assign-full")
    public String assignFull(@RequestParam String subjectName,
                              @RequestParam Long teacherId,
                              @RequestParam Long sectionId) {
         sectionService.assignSubjectTeacherAndSection(subjectName, teacherId, sectionId);
         return "success";
    }


    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
    }
}
