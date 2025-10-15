package com.MallareddyCollegeOfTechnology.app.Controllers.WorkStationControllers.WorkStationDTOs;


import lombok.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkStationDTO {

    private Long id;
    private String sectionName;
    private List<DailyRemainderDTO> dailyRemainders;
    private List<DailyTaskDTO> dailyTasks;
    private List<ScheduledTaskDTO> scheduledTasks;
    private List<ScheduledRemainderDTO> scheduledRemainders;

}
