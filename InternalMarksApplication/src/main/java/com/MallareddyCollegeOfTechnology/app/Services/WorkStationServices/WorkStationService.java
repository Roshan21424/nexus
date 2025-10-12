package com.MallareddyCollegeOfTechnology.app.Services.WorkStationServices;


import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.Student;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyRemainder;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyTask;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.WorkStation;
import com.MallareddyCollegeOfTechnology.app.Repositories.SectionRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.StudentRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.TeacherRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.WorkStationRepositories.DailyRemainderRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.WorkStationRepositories.DailyTaskRepository;
import com.MallareddyCollegeOfTechnology.app.Repositories.WorkStationRepositories.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
