package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Schedule;
import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PositionService positionService;
    private final SeparateService separateService;
    private final EmployeesController employeesController;
    private final EmployeesService employeesService;
    private LocalDate selectedMonth = LocalDate.now().withDayOfMonth(1);
    private Integer selectedDay;
    private String selectedEmployeeUsername;
    private final List<LocalDate> days = new ArrayList<>();
    private List<Employee> employeesList = new ArrayList<>();
    LinkedHashMap <Employee, List<Schedule>> employeesMonthlySchedule = new LinkedHashMap<>();
    private String firstname;
    private String lastname;
    private Separate separate;
    private Position position;

    public ScheduleController(ScheduleService scheduleService, PositionService positionService, SeparateService separateService, EmployeesController employeesController,EmployeesService employeesService) {
        this.scheduleService = scheduleService;
        this.positionService = positionService;
        this.separateService = separateService;
        this.employeesController = employeesController;
        this.employeesService = employeesService;
    }


    @GetMapping("/schedule")
    public String getMainPage(Model model){
        setMothDaysList();

        this.employeesList = employeesController.getEmployeesList();
        model.addAttribute("schedule", scheduleService.getSchedule());
        model.addAttribute("daysOfMonth", this.days);
        model.addAttribute("selectedMonth", this.selectedMonth);
        model.addAttribute("selectedDay", this.selectedDay);
        model.addAttribute("selectedEmployee", employeesService.getEmployeeByUsername(this.selectedEmployeeUsername));
        this.employeesList = employeesService.getEmployeesList();
        model.addAttribute("employees", this.employeesList);
        model.addAttribute("separatedList", separateService.getSeparates());
        model.addAttribute("positionsList", positionService.getPositions());
         employeesMonthlySchedule = scheduleService.employeesScheduleHashMapPerMonth(this.selectedMonth, this.employeesList);
        this.employeesList.forEach(System.out::println);
        model.addAttribute("monthlyEmployeesSchedule", employeesMonthlySchedule);
        List<String> dayOfWeeks = new ArrayList<>();
        this.days.forEach(d->{
            dayOfWeeks.add(d.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        });
        model.addAttribute("selectedMonthDayWeekShort", dayOfWeeks);
        model.addAttribute("monthlyEmployeesTotalHours", scheduleService.monthlyTotalHours(this.selectedMonth, employeesMonthlySchedule));
        model.addAttribute("loginUser", employeesService.getLoginEmployee());
        employeesMonthlySchedule.forEach((k,v) -> System.out.println(k + ": " + v.size()));
        return "schedule";
    }


    @GetMapping("/add-schedule")
    public String getScheduleForm() {
        return "redirect:/schedule";
    }

    @PostMapping("/add-schedule")
    public String createSchedule(@RequestParam Integer shiftStartHour,
                                 @RequestParam Integer shiftStartMinutes,
                                 @RequestParam Integer shiftEndHour,
                                 @RequestParam Integer shiftEndMinutes,
                                 @RequestParam (defaultValue = "true") boolean isPresent) {
        if(shiftStartMinutes == null){
            shiftStartMinutes = 0;
        }
        if(shiftEndMinutes == null){
            shiftEndMinutes = 0;
        }
        LocalDateTime shiftStart = this.selectedMonth.withDayOfMonth(this.selectedDay).atTime(shiftStartHour, shiftStartMinutes, 0, 0);
        LocalDateTime shiftEnd = this.selectedMonth.withDayOfMonth(this.selectedDay).atTime(shiftEndHour, shiftEndMinutes, 0, 0);
        double workedHours = Duration.between(shiftStart, shiftEnd).toMinutes()/60.0;
        Employee employee = employeesService.getEmployeeByUsername(this.selectedEmployeeUsername);
        System.out.println(employee.getFullName()+ ":"+shiftStart + " " + shiftEnd + "=" + workedHours);
        Schedule schedule = new Schedule(shiftStart, shiftEnd, workedHours, isPresent);
        System.out.println("Adding schedule");
        schedule.setEmployee(employee);
        scheduleService.saveSchedule(schedule);
        return "redirect:/schedule";
    }

    @GetMapping ("/select-employee/{employeeUsername}/select-day/{dayOfMonth}")
    public String selectEmployeeAndDayOfMonth(@PathVariable("employeeUsername")  String employeeUsername,
                                              @PathVariable("dayOfMonth") int dayOfMonth) {
        this.selectedEmployeeUsername = employeeUsername;
        this.selectedDay = dayOfMonth;
        System.out.println(this.selectedEmployeeUsername + " " + this.selectedDay);
        return "redirect:/schedule";
    }

    @GetMapping ("/select-day/{dayOfMonth}")
    public String selectDayOfMonth(@PathVariable("dayOfMonth") int dayOfMonth){
        this.selectedDay = dayOfMonth;
        return "redirect:/schedule";
    }

    private void setMothDaysList(){
        this.days.clear();
        for(int day = 0; day < this.selectedMonth.lengthOfMonth(); day++){
            this.days.add(this.selectedMonth.plusDays(day));
            System.out.println(this.selectedMonth.plusDays(day));
        }
    }

    @GetMapping("/month-list/")
    public String showMonthList(@RequestParam(value = "month", required = false) String selectedMonth) {
        this.selectedMonth = LocalDate.parse(selectedMonth+"-01");
        System.out.println("Received LocalDate: " + this.selectedMonth);
        this.days.clear();
        setMothDaysList();
        return "redirect:/schedule";
    }

    @GetMapping("/order-by-position-title")
    public String getScheduleSortedByPositionTitle() {
        employeesService.setEmployeesList(employeesService.getEmployeesList().stream().sorted(Comparator.comparing(employee -> employee.getPosition().getTitle().toLowerCase())).collect(Collectors.toList()));
        return "redirect:/schedule";
    }

    @GetMapping("/order-by-firstname")
    public String getScheduleSortedByFirstname() {
        employeesService.setEmployeesList(employeesService.getEmployeesList().stream().sorted(Comparator.comparing(employee -> employee.getFirstName().toLowerCase())).collect(Collectors.toList()));
        return "redirect:/schedule";
    }

    @GetMapping("/filter-employee-list")
    public String getScheduleFiltered(@RequestParam String employeeFirstName,
                                      @RequestParam String employeeLastName,
                                      @RequestParam Separate employeeSeparate,
                                      @RequestParam Position employeePosition) {
        List<Employee> filteredEmpolyeeList = employeesService.getFilteredEmployeesList("%"+employeeFirstName+"%","%"+employeeLastName+"%",employeeSeparate,employeePosition);
        employeesService.setEmployeesList(filteredEmpolyeeList);
        return "redirect:/schedule";
    }

    @GetMapping("/disable-filter")
    public String disableFilter() {
        employeesService.setEmployeesList(employeesService.employeesListByRole());
        return "redirect:/schedule";
    }
}
