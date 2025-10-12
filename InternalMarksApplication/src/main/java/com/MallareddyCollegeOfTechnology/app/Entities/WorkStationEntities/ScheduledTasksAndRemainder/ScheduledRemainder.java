package com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.ScheduledTasksAndRemainder;

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
@Table(name = "scheduled_remainder")
public class ScheduledRemainder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //(column) id of daily remainder

    private String description; //(column) description of the daily remainder

    @CreationTimestamp
    private LocalDateTime creationTime; //(column) creation time of the daily remainder

    private LocalDateTime expirationTime; //(column) creation time of the daily remainder


    private boolean forAll; //(column) is daily remainder for all

    @ManyToOne
    @JoinColumn(name="teacher_id",referencedColumnName = "id")
    private Teacher teacher; //(column) teacher of the daily remainder

    @ManyToOne
    @JoinColumn(name="workstation_id",referencedColumnName = "id")
    private WorkStation workStation; //(column) reference to the workstation

    @ManyToMany
    @JoinTable(name = "scheduled_remainder_student", joinColumns = @JoinColumn(name = "scheduled_remainder_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;
}
