package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Employees;
import com.example.AttendanceApp.models.Schedule;
import com.example.AttendanceApp.services.EmployeesService;
import com.example.AttendanceApp.services.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final EmployeesService employeesService;
    private LocalDate selectedMonth = LocalDate.now().withDayOfMonth(1);
    private List<LocalDate> days = new ArrayList<>();
    private List<Employees> employees = new ArrayList<>();

    public ScheduleController(ScheduleService scheduleService, EmployeesService employeesService) {
        this.scheduleService = scheduleService;
        this.employeesService = employeesService;
        this.employees = employeesService.getEmployees();
        setMothDaysList();
    }

    @GetMapping("/schedule")
    public String getMainPage(Model model){
        HashMap<Employees, List<Schedule>> employeesMonthlySchedule = scheduleService.employeesScheduleHashMapPerMonth(this.selectedMonth, this.employees);
        model.addAttribute("schedule", scheduleService.getSchedule());
        model.addAttribute("daysOfMonth", this.days);
        model.addAttribute("selectedMonth", this.selectedMonth);
        model.addAttribute("employees", this.employees);
        this.employees.forEach(System.out::println);
        model.addAttribute("monthlyEmployeesSchedule", employeesMonthlySchedule);
        model.addAttribute("monthlyEmployeesTotalHours", scheduleService.monthlyTotalHours(this.selectedMonth, employeesMonthlySchedule));
        employeesMonthlySchedule.forEach((k,v) -> System.out.println(k + ": " + v.size()));
        return "schedule";
    }


    @GetMapping("/add-schedule")
    public String getScheduleForm(Model model) {
        model.addAttribute("employees", this.employees);
        return "add-schedule";
    }

    @PostMapping("/add-schedule")
    public String createSchedule(@RequestParam long employeeId,
                                 @RequestParam LocalDateTime shiftStart,
                                 @RequestParam LocalDateTime shiftEnd,
                                 @RequestParam (defaultValue = "true") boolean isPresent) {

        Double workedHours = Duration.between(shiftStart, shiftEnd).toMinutes()/60.0;
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

    private void setMothDaysList(){
        for(int day = 0; day < this.selectedMonth.lengthOfMonth(); day++){
            if(this.selectedMonth != null){
                this.days.add(this.selectedMonth.plusDays(day));
                System.out.println(this.selectedMonth.plusDays(day));
            }
        }
    }

    @GetMapping("/month-list/")
    public String showMonthList(@RequestParam(value = "month", required = false) String selectedMonth, Model model) {
        System.out.println("Received String: " + selectedMonth);
        this.selectedMonth = LocalDate.parse(selectedMonth+"-01");
        System.out.println("Received LocalDate: " + this.selectedMonth);
        this.days.clear();
        setMothDaysList();
        return "redirect:/schedule";
    }
}
