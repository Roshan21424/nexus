package com.MallareddyCollegeOfTechnology.app.Repositories.WorkStationRepositories;

import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyRemainder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyRemainderRepository extends JpaRepository<DailyRemainder, Long> {

    List<DailyRemainder> findByWorkStationId(Long workStationId);

}
