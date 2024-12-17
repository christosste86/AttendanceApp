package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Schedule;
import com.example.AttendanceApp.repositaries.EmployeeRepository;
import com.example.AttendanceApp.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PositionService positionService;
    private final SeparateService separateService;
    private final EmployeesController employeesController;
    private LocalDate selectedMonth = LocalDate.now().withDayOfMonth(1);
    private List<LocalDate> days = new ArrayList<>();
    private List<Employee> employeesList = new ArrayList<>();

    public ScheduleController(ScheduleService scheduleService, PositionService positionService, SeparateService separateService, EmployeesController employeesController) {
        this.scheduleService = scheduleService;
        this.positionService = positionService;
        this.separateService = separateService;
        this.employeesController = employeesController;
    }


    @GetMapping("/")
    public String getMainPage(Model model){
        setMothDaysList();
        HashMap<Employee, List<Schedule>> employeesMonthlySchedule = scheduleService.employeesScheduleHashMapPerMonth(this.selectedMonth, this.employeesList);
        this.employeesList = employeesController.getEmployeesList();
        model.addAttribute("schedule", scheduleService.getSchedule());
        model.addAttribute("daysOfMonth", this.days);
        model.addAttribute("selectedMonth", this.selectedMonth);
        model.addAttribute("employees", employeesController.getEmployeesList());
        model.addAttribute("separatedList", separateService.getSeparates());
        model.addAttribute("positionsList", positionService.getPositions());
        this.employeesList.forEach(System.out::println);
        model.addAttribute("monthlyEmployeesSchedule", employeesMonthlySchedule);
        model.addAttribute("monthlyEmployeesTotalHours", scheduleService.monthlyTotalHours(this.selectedMonth, employeesMonthlySchedule));
        employeesMonthlySchedule.forEach((k,v) -> System.out.println(k + ": " + v.size()));
        return "index";
    }


    @GetMapping("/add-schedule")
    public String getScheduleForm(Model model) {
        model.addAttribute("employees", this.employeesList);
        return "redirect:/";
    }

    @PostMapping("/add-schedule")
    public String createSchedule(@RequestParam Employee employee,
                                 @RequestParam LocalDateTime shiftStart,
                                 @RequestParam LocalDateTime shiftEnd,
                                 @RequestParam (defaultValue = "true") boolean isPresent) {

        double workedHours = Duration.between(shiftStart, shiftEnd).toMinutes()/60.0;
        if(workedHours > 0 && workedHours <= 12){
            Schedule schedule = new Schedule(employee, shiftStart, shiftEnd, workedHours, isPresent);
            System.out.println("Adding schedule");
            schedule.setEmployee(employee);
            scheduleService.saveSchedule(schedule);
        }
        return "redirect:/";
    }

    private void setMothDaysList(){
        this.days.clear();
        for(int day = 0; day < this.selectedMonth.lengthOfMonth(); day++){
            if(this.selectedMonth != null){
                this.days.add(this.selectedMonth.plusDays(day));
                System.out.println(this.selectedMonth.plusDays(day));
            }
        }
    }

    @GetMapping("/month-list/")
    public String showMonthList(@RequestParam(value = "month", required = false) String selectedMonth) {
        System.out.println("Received String: " + selectedMonth);
        this.selectedMonth = LocalDate.parse(selectedMonth+"-01");
        System.out.println("Received LocalDate: " + this.selectedMonth);
        this.days.clear();
        setMothDaysList();
        return "redirect:/";
    }
}
