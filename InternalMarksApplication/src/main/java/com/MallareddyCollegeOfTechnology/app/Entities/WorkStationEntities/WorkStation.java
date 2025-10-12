package com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities;

import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyRemainder;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyTask;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WorkStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //(column) id of workstation

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Section section; //(column) reference to section id

    @OneToMany(mappedBy = "workStation")
    private Set<DailyRemainder> dailyRemainders; //daily remainders of the workstation

    @OneToMany(mappedBy = "workStation")
    private Set<DailyTask> dailyTasks; //daily tasks of the workstation

}
