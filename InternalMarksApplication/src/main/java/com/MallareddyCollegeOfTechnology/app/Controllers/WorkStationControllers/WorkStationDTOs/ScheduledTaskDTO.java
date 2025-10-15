package com.MallareddyCollegeOfTechnology.app.Controllers.WorkStationControllers.WorkStationDTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScheduledTaskDTO {
    private Long id;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime expirationTime;
    private String status;
    private boolean forAll;
    private String fromTeacher;
    private List<String> toStudents;
}
