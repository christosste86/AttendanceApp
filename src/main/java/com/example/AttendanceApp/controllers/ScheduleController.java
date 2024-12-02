package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Schedule;
import com.example.AttendanceApp.services.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDateTime;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public String getMainPage(Model model){
        model.addAttribute("schedule", scheduleService.getSchedule());
        return "schedule";
    }

    @GetMapping("/add-schedule")
    public String getScheduleForm() {
        return "add-schedule";
    }


    @PostMapping("/add-schedule")
    public String createSchedule(@RequestParam long employeeId,
                                 @RequestParam LocalDateTime shiftStart,
                                 @RequestParam LocalDateTime shiftEnd,
                                 @RequestParam (required = true, defaultValue = "true") boolean isPresent) {

        Double workedHours = scheduleService.workedHours(shiftStart,shiftEnd);
        isPresent = scheduleService.isPresent(workedHours);
        System.out.println("Received Data: " + employeeId + ", " + shiftStart + ", " + shiftEnd + ", " + workedHours + ", " + isPresent);

        Schedule schedule = new Schedule(employeeId, shiftStart, shiftEnd, workedHours, isPresent);
            System.out.println("Adding schedule");
            scheduleService.saveSchedule(schedule);

            // TODO condition - add only if it doesn't exist already
//        if (!scheduleService.scheduleExists(employeeId, shiftStart, shiftEnd, workedHours, isPresent)) {
//            System.out.println("Adding schedule");
//            scheduleService.saveSchedule(schedule);
//        }
        return "redirect:/schedule";
    }
}
