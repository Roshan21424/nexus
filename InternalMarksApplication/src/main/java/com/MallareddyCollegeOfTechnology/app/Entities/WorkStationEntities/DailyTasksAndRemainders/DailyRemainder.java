package com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders;


import com.MallareddyCollegeOfTechnology.app.Entities.Student;
import com.MallareddyCollegeOfTechnology.app.Entities.Teacher;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.WorkStation;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "daily_remainder")
public class DailyRemainder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //(column) id of daily remainder

    private String description; //(column) description of the daily remainder

    @CreationTimestamp
    private LocalDateTime creationTime; //(column) creation time of the daily remainder

    private boolean forAll; //(column) is daily remainder for all

    @ManyToOne
    @JoinColumn(name="teacher_id",referencedColumnName = "id")
    private Teacher teacher; //(column) teacher of the daily remainder

    @ManyToOne
    @JoinColumn(name="workstation_id",referencedColumnName = "id")
    private WorkStation workStation; //(column) reference to the workstation

    @ManyToMany
    @JoinTable(name = "remainder_student", joinColumns = @JoinColumn(name = "remainder_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

}
