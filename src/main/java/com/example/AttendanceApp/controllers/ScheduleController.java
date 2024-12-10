package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Schedule;
import com.example.AttendanceApp.services.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private LocalDate selectedMonth;
    private List<LocalDate> days = new ArrayList<>();

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public String getMainPage(Model model){
        model.addAttribute("schedule", scheduleService.getSchedule());
        model.addAttribute("daysOfMonth", this.days);
        model.addAttribute("selectedMonth", this.selectedMonth);
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
                                 @RequestParam Double workedHours,
                                 @RequestParam boolean isPresent) {

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


    @GetMapping("/month-list/")
    public String showMonthList(@RequestParam(value = "month", required = false) String selectedMonth, Model model) {
        System.out.println("Received String: " + selectedMonth);
        this.selectedMonth = LocalDate.parse(selectedMonth+"-01");
        System.out.println("Received LocalDate: " + this.selectedMonth);
        this.days.clear();
        for(int day = 0; day < this.selectedMonth.lengthOfMonth(); day++){
            if(this.selectedMonth != null){
                this.days.add(this.selectedMonth.plusDays(day));
                System.out.println(this.selectedMonth.plusDays(day));
            }
        }
        return "redirect:/schedule";
    }
}
