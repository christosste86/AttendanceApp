package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.*;
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

    //services
    private final ScheduleService scheduleService;
    private final PositionService positionService;
    private final SeparateService separateService;
    private final EmployeesService employeesService;
    private final FavoriteShiftService favoriteShiftService;

    //selected data
    private LocalDate selectedMonth = LocalDate.now().withDayOfMonth(1);
    private LocalDate selectedDay;
    private Employee selectedEmployee;
    private Schedule selectedSchedule;

    //list of days and Employees
    private final List<LocalDate> days = new ArrayList<>();
    private List<Employee> employeesList = new ArrayList<>();
    private LinkedHashMap <Employee, List<Schedule>> employeesMonthlySchedule = new LinkedHashMap<>();

    //css classes
    private String scheduleFormClass = "hide";
    private String selectedDayClass = "selected";

    public ScheduleController(ScheduleService scheduleService, PositionService positionService, SeparateService separateService, EmployeesService employeesService, FavoriteShiftService favoriteShiftService) {
        this.scheduleService = scheduleService;
        this.positionService = positionService;
        this.separateService = separateService;
        this.employeesService = employeesService;
        this.favoriteShiftService = favoriteShiftService;
    }


    @GetMapping("/schedule")
    public String getMainPage(Model model){
        setMothDaysList();

        model.addAttribute("schedule", scheduleService.getSchedule());
        model.addAttribute("daysOfMonth", this.days);
        model.addAttribute("selectedMonth", this.selectedMonth);
        model.addAttribute("selectedDay", this.selectedDay);
        model.addAttribute("selectedEmployee", this.selectedEmployee);
        model.addAttribute("selectedSchedule", this.selectedSchedule);
        this.employeesList = employeesService.getEmployeesList();
        model.addAttribute("employees", this.employeesList);
        model.addAttribute("separatedList", separateService.getSeparates());
        model.addAttribute("positionsList", positionService.getPositions());
        employeesMonthlySchedule = scheduleService.employeesScheduleHashMapPerMonth(this.selectedMonth, this.employeesList);
        model.addAttribute("monthlyEmployeesSchedule", employeesMonthlySchedule);
        List<String> dayOfWeeks = new ArrayList<>();
        this.days.forEach(d->{
            dayOfWeeks.add(d.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        });
        model.addAttribute("selectedMonthDayWeekShort", dayOfWeeks);
        model.addAttribute("monthlyEmployeesTotalHours", scheduleService.monthlyTotalHours(this.selectedMonth, employeesMonthlySchedule));
        model.addAttribute("loginUser", employeesService.getLoginEmployee());
        model.addAttribute("favoriteShiftList", favoriteShiftService.getFavoriteShifts());
        model.addAttribute("scheduleFormClass", scheduleFormClass);
        model.addAttribute("selectedDayClass", this.selectedDayClass);
        return "schedule";
    }


    @PostMapping("/add-schedule")
    public String createSchedule(@RequestParam Integer shiftStartHour,
                                 @RequestParam Integer shiftStartMinutes,
                                 @RequestParam Integer shiftEndHour,
                                 @RequestParam Integer shiftEndMinutes,
                                 @RequestParam (defaultValue = "true") boolean isPresent,
                                 Model model) {
        model.addAttribute("addScheduleClass", "openAddSchedule" );
        if(shiftStartMinutes == null){
            shiftStartMinutes = 0;
        }
        if(shiftEndMinutes == null){
            shiftEndMinutes = 0;
        }
        LocalDateTime shiftStart = this.selectedMonth.withDayOfMonth(this.selectedDay.getDayOfMonth()).atTime(shiftStartHour, shiftStartMinutes, 0, 0);
        LocalDateTime shiftEnd = this.selectedMonth.withDayOfMonth(this.selectedDay.getDayOfMonth()).atTime(shiftEndHour, shiftEndMinutes, 0, 0);
        Employee employee = employeesService.getEmployeeByUsername(this.selectedEmployee.getUsername());
        Schedule schedule = new Schedule(shiftStart, shiftEnd, isPresent);
        schedule.setEmployee(employee);
        if(scheduleService.isEmployeeDayExist(employee,shiftStart.getYear(), shiftStart.getMonthValue(), shiftStart.getDayOfMonth())){
            Schedule existedSchedule = scheduleService.getScheduleByEmployeeDate(employee,shiftStart.getYear(), shiftStart.getMonthValue(), shiftStart.getDayOfMonth());
            scheduleService.updateScheduleById(existedSchedule.getId(), shiftStart, shiftEnd);
        }else{
            scheduleService.saveSchedule(schedule);
        }
        if(shiftStartHour >= 5 && shiftEndHour <= 16){
            favoriteShiftService.updateFavoriteShift(1, shiftStart, shiftEnd);
        }
        else if(shiftStartHour >= 11 && shiftEndHour <= 23){
            favoriteShiftService.updateFavoriteShift(2, shiftStart, shiftEnd);
        }else{
            favoriteShiftService.updateFavoriteShift(3, shiftStart, shiftEnd);
        }
        this.scheduleFormClass ="hide";
        return "redirect:/schedule";

    }

//Create day shift for employee from favorites
    @GetMapping("/add-schedule-from-favorite/{shiftId}")
    public String getScheduleFromFavorite(@PathVariable("shiftId") long shiftId) {
    return "redirect:/schedule";
}

    @PostMapping("/add-schedule-from-favorite/{shiftId}")
    public String createScheduleFromFavorite(@PathVariable("shiftId") long shiftId){

        FavoriteShift favoriteShift = favoriteShiftService.getFavoriteShiftById(shiftId);

        Employee employee = employeesService.getEmployeeByUsername(this.selectedEmployee.getUsername());
        Schedule schedule = new Schedule(
                this.selectedMonth.withDayOfMonth(this.selectedDay.getDayOfMonth()).atTime(favoriteShift.getShiftStart()),
                this.selectedMonth.withDayOfMonth(this.selectedDay.getDayOfMonth()).atTime(favoriteShift.getShiftEnd()),
                true);
        schedule.setEmployee(employee);
        if(scheduleService.isEmployeeDayExist(employee, this.selectedMonth.getYear(), this.selectedMonth.getMonthValue(), this.selectedMonth.getDayOfMonth())){
            Schedule existedSchedule = scheduleService.getScheduleByEmployeeDate(employee,schedule.getShiftStart().getYear(), schedule.getShiftStart().getMonthValue(), schedule.getShiftStart().getDayOfMonth());
            scheduleService.updateScheduleById(existedSchedule.getId(), schedule.getShiftStart(), schedule.getShiftStart());
        }else{
            scheduleService.saveSchedule(schedule);
        }
        this.scheduleFormClass ="hide";
        return "redirect:/schedule";
    }

    @GetMapping("/delete-employee-day-shift")
    public String deleteEmployeeDayShift() {
        Employee employee = employeesService.getEmployeeByUsername(this.selectedEmployee.getUsername());
        Schedule schedule = scheduleService.getScheduleByEmployeeDate(employee, this.selectedMonth.getYear(), this.selectedMonth.getMonthValue(), this.selectedDay.getDayOfMonth());
        if(schedule.getShiftStart() != null && schedule.getShiftEnd() != null){
            scheduleService.deleteSchedule(schedule);
        }
        this.scheduleFormClass = "hide";
        return "redirect:/schedule";
    }


    //select
    @GetMapping ("/select-employee/{employeeUsername}/select-day/{dayOfMonth}")
    public String selectEmployeeAndDayOfMonth(@PathVariable("employeeUsername")  String employeeUsername,
                                              @PathVariable("dayOfMonth") int dayOfMonth) {
        this.scheduleFormClass = "openAddSchedule";
        this.selectedEmployee = employeesService.getEmployeeByUsername(employeeUsername);
        this.selectedDay = LocalDate.of(this.selectedMonth.getYear(), this.selectedMonth.getMonthValue(), dayOfMonth);
        this.selectedSchedule = scheduleService.getScheduleByEmployeeDate(this.selectedEmployee,this.selectedDay.getYear(), this.selectedDay.getMonthValue(), this.selectedDay.getDayOfMonth());
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

    //Order by
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

    //Filters
    @GetMapping("/filter-employee-list")
    public String getScheduleFiltered(@RequestParam String employeeFirstName,
                                      @RequestParam String employeeLastName,
                                      @RequestParam Separate employeeSeparate,
                                      @RequestParam Position employeePosition) {
        List<Employee> filteredEmpolyeeList = new ArrayList<>();
        if(employeeSeparate == null && employeePosition != null){
            filteredEmpolyeeList = employeesService.getFilteredEmployeesListByFirstNameLastNamePosition(
                    employeeFirstName, employeeLastName, employeePosition);
            employeesService.setEmployeesList(filteredEmpolyeeList);
        }else if(employeePosition == null && employeeSeparate != null){
            filteredEmpolyeeList = employeesService.getFilteredEmployeesListByFirstNameLastNameSeparate(
                    employeeFirstName, employeeLastName, employeeSeparate);
            employeesService.setEmployeesList(filteredEmpolyeeList);
        }else if(employeeSeparate == null && employeePosition == null){
            filteredEmpolyeeList = employeesService.getFilteredEmployeesListByFirstNameLastName(
                    employeeFirstName, employeeLastName);
            employeesService.setEmployeesList(filteredEmpolyeeList);
        }else{
            filteredEmpolyeeList = employeesService.getFilteredEmployeesListByFirstNameLastNameSeparatePosition(
                    employeeFirstName, employeeLastName, employeeSeparate, employeePosition);
            employeesService.setEmployeesList(filteredEmpolyeeList);
        }

        return "redirect:/schedule";
    }

    @GetMapping("/disable-filter")
    public String disableFilter() {
        employeesService.setEmployeesList(employeesService.employeesListByRole());
        return "redirect:/schedule";
    }

    //change classes
    @GetMapping("/close-schedule-form")
    public String closeScheduleForm() {
        this.scheduleFormClass = "hide";
        return "redirect:/schedule";
    }

}
