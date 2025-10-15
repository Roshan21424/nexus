package com.MallareddyCollegeOfTechnology.app.Repositories.WorkStationRepositories;

import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyTask;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.ScheduledTasksAndRemainder.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduledTasksRepository extends JpaRepository<ScheduledTask,Long> {
    List<ScheduledTask> findByWorkStationId(Long workStationId);

}
