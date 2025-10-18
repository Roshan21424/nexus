package com.MallareddyCollegeOfTechnology.app.Controllers.AdminController;

import com.MallareddyCollegeOfTechnology.app.Controllers.AdminController.DTOs.CreateSectionDTO;
import com.MallareddyCollegeOfTechnology.app.Controllers.AdminController.DTOs.CreateSubjectTeachersDTO;
import com.MallareddyCollegeOfTechnology.app.Controllers.ClassController.DTOs.GetClassDTO;
import com.MallareddyCollegeOfTechnology.app.Controllers.ClassController.DTOs.StudentDTO;
import com.MallareddyCollegeOfTechnology.app.Controllers.ClassController.DTOs.SubjectDTO;
import com.MallareddyCollegeOfTechnology.app.Entities.*;
import com.MallareddyCollegeOfTechnology.app.Entities.ClassRepositoryEntities.ClassRepositoryFile;
import com.MallareddyCollegeOfTechnology.app.Repositories.*;
import com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ClassRepositoryFileRepository classRepositoryFileRepository;

    @PostMapping("/create-student")
    private Student createTeacher(@RequestParam String name, @RequestParam String password){

        Student student = new Student();
        student.setName(name);
        student.setPassword(password);
        student.setRole(User.Role.STUDENT);
        return  studentRepository.save(student);
    }

    @PostMapping("/create-teacher")
    private Teacher createStudent(@RequestParam String name,@RequestParam String password){
        Teacher teacher  = new Teacher();
        teacher.setName(name);
        teacher.setPassword(password);
        teacher.setRole(User.Role.TEACHER);
        return teacherRepository.save(teacher);
    }

    @PostMapping("/create-subject-teachers")
    private String createSubjectTeachers(@RequestBody List<CreateSubjectTeachersDTO> createSubjectTeachersDTOs){

        createSubjectTeachersDTOs.forEach(createSubjectTeachersDTO -> {

            //get the teacher
            Teacher teacher = teacherRepository.findById(createSubjectTeachersDTO.getTeacherId()).orElseThrow(()->new RuntimeException("teacher not found"));

            //create a new subject
            Subject subject = new Subject();
            subject.setName(createSubjectTeachersDTO.getSubjectName()); //set the name of the subject
            subject.setTeacher(teacher);//set the teacher for the subject

           subjectRepository.save(subject);
        });

        return "done";
    }

    @GetMapping("/get-class")
    public GetClassDTO getClassDetails(@RequestParam Long id) {

        //get the current user
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));

//        if (user.getRole() == User.Role.TEACHER) {

        //get the teacher
        Teacher teacher = teacherRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("teacher not found"));

        //get the section
        Section section = teacher.getClassTeacherOf();


        //create studentDTOs
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : section.getStudents()) {
            StudentDTO studentDTO = new StudentDTO(student.getId(), student.getName());
            studentDTOS.add(studentDTO);
        }

        //create subjectDTOs
        List<SubjectDTO> subjectDTOs = new ArrayList<>();
        for (Subject subject : section.getSubjects()) {
            SubjectDTO subjectDTO = new SubjectDTO(subject.getId(), subject.getName(), subject.getTeacher().getName());
            subjectDTOs.add(subjectDTO);
        }


        GetClassDTO getClassDTO = new GetClassDTO();
        getClassDTO.setSectionName(section.getSectionEnum().name());//setting the section name
        getClassDTO.setClassTeacherName(teacher.getName());//setting the teacher name
        getClassDTO.setStudentDTOList(studentDTOS);//setting the students
        getClassDTO.setSubjectDTOList(subjectDTOs);//setting the subjects
        getClassDTO.setEvents(section.getEvents());//setting the subjects
        getClassDTO.setTimetableImageBase64(section.getTimetableImageBase64());


        return getClassDTO;
//        }

    }


    @PostMapping("/create-section")
    private String createSection(@RequestBody CreateSectionDTO createSectionDTO){

        Teacher classTeacher = teacherRepository.findById(createSectionDTO.getTeacherId()).orElseThrow(()->new RuntimeException("can't find class teacher"));
        List<Student> students = studentRepository.findAllById(createSectionDTO.getStudentsIds());
        List<Subject> subjects = subjectRepository.findAllById(createSectionDTO.getSubjectIds());

        Section section = new Section();
        section.setSectionEnum(Section.SectionEnum.MECH_2025_A);//set the section name
        section.setStudents(new HashSet<>(students));//set the students
        sectionRepository.save(section);

        subjects.forEach(subject -> subject.setSection(section));
        classTeacher.setClassTeacherOf(section); //setting the class teacher
        subjectRepository.saveAll(subjects);
        teacherRepository.save(classTeacher);

        return "done";
    }

    @PostMapping("/add-events")
    private String createEvents(@RequestBody String events){
        Section section = sectionRepository.findById(1L).orElseThrow(()->new RuntimeException("can't find section"));
        section.setEvents(events);
        sectionRepository.save(section);
        return "done";
    }

    @PostMapping("/add-repository")
    private String createRepository(@RequestBody ClassRepositoryFile classRepositoryFile,@RequestParam Long sectionId){
        Section section =sectionRepository.findById(sectionId).orElseThrow(()->new RuntimeException("section not found"));

        classRepositoryFile.setSection(section);
        classRepositoryFileRepository.save(classRepositoryFile);
        return "done";
    }

    @PostMapping("/get-repository")
    private List<ClassRepositoryFile> getRepository(@RequestParam Long sectionId){
        return classRepositoryFileRepository.findBySection_Id(sectionId);
    }

    @PostMapping("/add-timetable/{sectionId}")
    private String addTimeTable(   @PathVariable Long sectionId,
                                   @RequestParam("file") MultipartFile file) throws IOException {
    System.out.println("section is:"+sectionId);
     Section section =  sectionRepository.findById(sectionId).orElseThrow(()->new RuntimeException("section not found"));
        String base64Image = "data:" + file.getContentType() + ";base64," +
                Base64.getEncoder().encodeToString(file.getBytes());

        section.setTimetableImageBase64(base64Image);
        sectionRepository.save(section);
        return "done";
    }

}
