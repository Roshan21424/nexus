package com.MallareddyCollegeOfTechnology.app.Repositories.WorkStationRepositories;

import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyTask;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.ScheduledTasksAndRemainder.ScheduledRemainder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduledRemaindersRepository extends JpaRepository<ScheduledRemainder,Long> {
    List<ScheduledRemainder> findByWorkStationId(Long workStationId);

}
