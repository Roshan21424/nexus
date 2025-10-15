package com.MallareddyCollegeOfTechnology.app.Services.WorkStationServices;


import com.MallareddyCollegeOfTechnology.app.Controllers.WorkStationControllers.WorkStationDTOs.*;
import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Student;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import com.MallareddyCollegeOfTechnology.app.Entities.User;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyRemainder;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyTask;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.ScheduledTasksAndRemainder.ScheduledRemainder;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.ScheduledTasksAndRemainder.ScheduledTask;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.WorkStation;
import com.MallareddyCollegeOfTechnology.app.Repositories.SectionRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.StudentRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.TeacherRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.WorkStationRepositories.*;
import com.MallareddyCollegeOfTechnology.app.SecurityConfigurations.Services.AuthService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class WorkStationService {

    @Autowired
    private WorkStationRepository workStationRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private DailyRemainderRepository dailyRemainderRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DailyTaskRepository dailyTaskRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ScheduledTasksRepository scheduledTasksRepository;
    @Autowired
    private ScheduledRemaindersRepository scheduledRemaindersRepository;

    public WorkStation addWorkStation(WorkStation workStation,Long sectionId){

        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new RuntimeException("Section not found"));;
        workStation.setSection(section);
       return workStationRepository.save(workStation);
    }

    public DailyRemainder addDailyRemainder(DailyRemainder dailyRemainder,Long workstationId,Long teacherId, Set<Long> studentIds){


        System.out.println("1");
        //get the workstation
        WorkStation workstation = workStationRepository.findById(workstationId).orElseThrow(() -> new RuntimeException("WorkStation not found"));
        System.out.println("2");

        //get the teacher
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher not found"));
        System.out.println("3");

        dailyRemainder.setWorkStation(workstation); //set the workstation
        System.out.println("4");

        dailyRemainder.setTeacher(teacher); //set the teacher
        System.out.println("5");

        if (!dailyRemainder.isForAll() && studentIds != null && !studentIds.isEmpty()) {
            System.out.println("6");

            Set<Student> students = studentRepository.findAllById(studentIds).stream().collect(java.util.stream.Collectors.toSet());
            System.out.println("7");

            dailyRemainder.setStudents(students); //set the students
        }

        System.out.println("8");

        return dailyRemainderRepository.save(dailyRemainder);

    }

    public DailyTask addDailyTask(DailyTask dailyTask, Long workstationId, Long teacherId, Set<Long> studentIds){


        System.out.println("1");
        //get the workstation
        WorkStation workstation = workStationRepository.findById(workstationId).orElseThrow(() -> new RuntimeException("WorkStation not found"));
        System.out.println("2");

        //get the teacher
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher not found"));
        System.out.println("3");

        dailyTask.setWorkStation(workstation); //set the workstation
        System.out.println("4");

        dailyTask.setTeacher(teacher); //set the teacher
        System.out.println("5");

        if (!dailyTask.isForAll() && studentIds != null && !studentIds.isEmpty()) {
            System.out.println("6");

            Set<Student> students = studentRepository.findAllById(studentIds).stream().collect(java.util.stream.Collectors.toSet());
            System.out.println("7");

            dailyTask.setStudents(students); //set the students
        }

        dailyTask.setStatus("Pending");
        System.out.println("8");

        return dailyTaskRepository.save(dailyTask);

    }

//    public WorkStation getWorkStation(Long workStationId) {
//        return  workStationRepository.findById(workStationId).orElseThrow();
//    }




    public WorkStationDTO getWorkStation(Long sectionId) {
        System.out.println(1);

        // Get the workstation from section id
        WorkStation workStation = workStationRepository.findBySection_Id(sectionId);
        if (workStation == null) {
            throw new RuntimeException("WorkStation not found for sectionId: " + sectionId);
        }

        System.out.println(2);

        // Fetch DailyRemainders directly from repository (Option B)
        List<DailyRemainder> dailyRemainders = dailyRemainderRepository.findByWorkStationId(workStation.getId());
        if (dailyRemainders.isEmpty()) {
            System.out.println("No daily remainders found for this workstation.");
        } else {
            dailyRemainders.forEach(dr -> {
                System.out.println("ID: " + dr.getId());
                System.out.println("Description: " + dr.getDescription());
                System.out.println("For All: " + dr.isForAll());
                System.out.println("Creation Time: " + dr.getCreationTime());
                if (dr.getTeacher() != null) {
                    System.out.println("Teacher: " + dr.getTeacher().getName());
                }
                if (dr.getStudents() != null && !dr.getStudents().isEmpty()) {
                    System.out.print("Students: ");
                    dr.getStudents().forEach(student -> System.out.print(student.getName() + ", "));
                    System.out.println();
                }
                System.out.println("---------------------------");
            });
        }

        // Fetch the other sets from workstation (still lazy, but usually fine)
        List<DailyTask> dailyTasks = dailyTaskRepository.findByWorkStationId(workStation.getId());
        List<ScheduledRemainder> scheduledRemainders = scheduledRemaindersRepository.findByWorkStationId(workStation.getId());
        List<ScheduledTask> scheduledTasks = scheduledTasksRepository.findByWorkStationId(workStation.getId());

        System.out.println(3);

        // Convert to DTOs
        List<DailyRemainderDTO> dailyRemaindersList = dailyRemainders.stream()
                .map(this::mapToDailyRemainderDTO)
                .toList();
        List<DailyTaskDTO> dailyTasksList = dailyTasks.stream()
                .map(this::mapToDailyTaskDTO)
                .toList();
        List<ScheduledRemainderDTO> scheduledRemaindersList = scheduledRemainders.stream()
                .map(this::mapToScheduledRemainderDTO)
                .toList();
        List<ScheduledTaskDTO> scheduledTasksList = scheduledTasks.stream()
                .map(this::mapToScheduledTaskDTO)
                .toList();

        System.out.println(4);

        // Create WorkStationDTO
        WorkStationDTO dto = new WorkStationDTO();
        dto.setId(workStation.getId());
        dto.setSectionName(workStation.getSection().getBranchName());
        dto.setDailyRemainders(dailyRemaindersList);
        dto.setDailyTasks(dailyTasksList);
        dto.setScheduledRemainders(scheduledRemaindersList);
        dto.setScheduledTasks(scheduledTasksList);

        return dto;
    }



    //DTO mappers
    private DailyRemainderDTO mapToDailyRemainderDTO(DailyRemainder remainder) {
        DailyRemainderDTO dto = new DailyRemainderDTO();
        dto.setId(remainder.getId());
        dto.setDescription(remainder.getDescription());
        dto.setCreationTime(remainder.getCreationTime());
        dto.setForAll(remainder.isForAll());
        dto.setFromTeacher(remainder.getTeacher() != null ? remainder.getTeacher().getName() : null);
        dto.setToStudents(
                remainder.getStudents() != null
                        ? remainder.getStudents().stream().map(Student::getName).toList()
                        : List.of()
        );
        return dto;
    }
    private ScheduledRemainderDTO mapToScheduledRemainderDTO(ScheduledRemainder remainder) {
        ScheduledRemainderDTO dto = new ScheduledRemainderDTO();
        dto.setId(remainder.getId());
        dto.setDescription(remainder.getDescription());
        dto.setCreationTime(remainder.getCreationTime());
        dto.setExpirationTime(remainder.getExpirationTime());
        dto.setForAll(remainder.isForAll());
        dto.setFromTeacher(remainder.getTeacher() != null ? remainder.getTeacher().getName() : null);
        dto.setToStudents(
                remainder.getStudents() != null
                        ? remainder.getStudents().stream().map(Student::getName).toList()
                        : List.of()
        );
        return dto;
    }
    private ScheduledTaskDTO mapToScheduledTaskDTO(ScheduledTask task) {
        ScheduledTaskDTO dto = new ScheduledTaskDTO();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setStartTime(task.getStartTime());
        dto.setExpirationTime(task.getExpirationTime());
        dto.setStatus(task.getStatus());
        dto.setForAll(task.isForAll());
        dto.setFromTeacher(task.getTeacher() != null ? task.getTeacher().getName() : null);
        dto.setToStudents(
                task.getStudents() != null
                        ? task.getStudents().stream().map(Student::getName).toList()
                        : List.of()
        );
        return dto;
    }
    private DailyTaskDTO mapToDailyTaskDTO(DailyTask task) {
        DailyTaskDTO dto = new DailyTaskDTO();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setStartTime(task.getStartTime());
        dto.setStatus(task.getStatus());
        dto.setForAll(task.isForAll());
        dto.setFromTeacher(task.getTeacher() != null ? task.getTeacher().getName() : null);
        dto.setToStudents(
                task.getStudents() != null
                        ? task.getStudents().stream().map(Student::getName).toList()
                        : List.of()
        );
        return dto;
    }








}
