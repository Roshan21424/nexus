package com.MallareddyCollegeOfTechnology.app.Controllers.WorkStationControllers;

import com.MallareddyCollegeOfTechnology.app.Controllers.WorkStationControllers.WorkStationDTOs.WorkStationDTO;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyRemainder;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.DailyTasksAndRemainders.DailyTask;
import com.MallareddyCollegeOfTechnology.app.Entities.WorkStationEntities.WorkStation;
import com.MallareddyCollegeOfTechnology.app.Services.WorkStationServices.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/workstations")
public class WorkStationController {

  @Autowired
  private WorkStationService workStationService;

  @PostMapping("/{sectionId}")
  private String addWorkstation(@RequestBody WorkStation workStation, @PathVariable Long sectionId){
      workStationService.addWorkStation(workStation,sectionId);
      return "done";
  }

  @PostMapping("/set-daily-remainder")
  private String addDailyRemainder(@RequestBody DailyRemainder dailyRemainder, @RequestParam Long workStationId,@RequestParam Long teacherId,@RequestParam Set<Long> studentsId) {

    workStationService.addDailyRemainder(dailyRemainder,workStationId,teacherId,studentsId);

    return "done";
  }

  @PostMapping("/set-daily-tasks")
  private String addDailyTasks(@RequestBody DailyTask dailyTask, @RequestParam Long workStationId, @RequestParam Long teacherId, @RequestParam Set<Long> studentsId) {

    workStationService.addDailyTask(dailyTask,workStationId,teacherId,studentsId);

    return "done";
  }

  @GetMapping("/get-workstation/{workStationId}")
  private WorkStationDTO getSectionWorkstation(@PathVariable Long workStationId){
    return workStationService.getWorkStation(workStationId);
  }



}
