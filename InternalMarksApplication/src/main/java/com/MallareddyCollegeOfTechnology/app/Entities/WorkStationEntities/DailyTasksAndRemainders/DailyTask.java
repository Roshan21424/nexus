package com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders;

import com.MallareddyCollegeOfTechnology.app.Entities.Student;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.WorkStation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="daily_task")
public class DailyTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //(column) id of the daily task

    private String description;//(column) description of the daily task

    @CreationTimestamp
    private LocalDateTime startTime;//(column) time of the daily task

    private String status;//(column) status of the daily task


    private boolean forAll;//(column) is the task for all

    @ManyToOne
    @JoinColumn(name = "teacher_id",referencedColumnName = "id")
    private Teacher teacher;//(column) teacher of the daily task

    @ManyToOne
    @JoinColumn(name="workstation_id",referencedColumnName = "id")
    private WorkStation workStation; //(column) reference to the workstation

    @ManyToMany
    @JoinTable(name = "task_student",joinColumns = @JoinColumn(name = "task_id"),inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;//students the task assigned to

}
