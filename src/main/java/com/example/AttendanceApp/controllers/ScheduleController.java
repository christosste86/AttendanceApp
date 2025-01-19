package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.*;
import com.example.AttendanceApp.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private LocalDate startLocalDate = this.selectedMonth;
    private LocalDate endLocalDate = this.selectedMonth.withDayOfMonth(this.selectedMonth.lengthOfMonth());
    private LocalDate selectedDay;
    private Employee selectedEmployee;
    private Schedule selectedSchedule;
    private boolean isPresent;
    private String note;

    //list of days and Employees
    private List<Employee> employeesList = new ArrayList<>();

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
        model.addAttribute("schedule", scheduleService.getSchedules());
        model.addAttribute("daysOfMonth", getDaysOfPeriod());
        model.addAttribute("selectedMonth", this.selectedMonth);
        model.addAttribute("selectedDay", this.selectedDay);
        model.addAttribute("startLocalDate", this.startLocalDate);
        model.addAttribute("endLocalDate", this.endLocalDate);
        model.addAttribute("selectedEmployee", this.selectedEmployee);
        model.addAttribute("selectedSchedule", this.selectedSchedule);
        this.employeesList = employeesService.getEmployeesList();
        model.addAttribute("employees", this.employeesList);
        model.addAttribute("separatedList", separateService.getSeparates());
        model.addAttribute("positionsList", positionService.getPositions());
        model.addAttribute("getEmployeesScheduleForPeriod", getEmployeesScheduleForPeriod());
        model.addAttribute("selectedMonthDayWeekShort", getDayOfWeek(this.startLocalDate, this.endLocalDate));
        model.addAttribute("periodEmployeeDetails", getPeriodEmployeeDetails());
        model.addAttribute("loginUser", employeesService.getLoginEmployee());
        model.addAttribute("favoriteShiftList", favoriteShiftService.getFavoriteShifts());
        model.addAttribute("getScheduleByEmployeeLastWeek", getScheduleByEmployeeLastWeek());
        model.addAttribute("loginUser", employeesService.getLoginEmployee());
        //css clases
        model.addAttribute("scheduleFormClass", scheduleFormClass);
        model.addAttribute("selectedDayClass", this.selectedDayClass);
        return "schedule";
    }

    private List<Schedule> getScheduleByEmployeeLastWeek(){
        List<Schedule> scheduleList = new ArrayList<>();
        this.employeesList.forEach(employee -> {
            scheduleService.getScheduleByEmployeeBetweenDays(employee, LocalDate.now().minusDays(7), LocalDate.now()).forEach(scheduleList::add);
        });
        return scheduleList
                .stream()
                .sorted(Comparator.comparing(Schedule::getShiftStart))
                .toList();
    }

    private LinkedHashMap<Employee, Details> getPeriodEmployeeDetails(){
        return scheduleService.getPeriodDetailsForEmployees(
                this.employeesList,
                this.selectedMonth,
                this.selectedMonth.withDayOfMonth(selectedMonth.lengthOfMonth()));
    }

    private LinkedHashMap<Employee, LinkedHashMap<LocalDate, Schedule>> getEmployeesScheduleForPeriod(){
        return scheduleService.getEmployeesScheduleForPeriod(
                this.employeesList,
                this.startLocalDate,
                this.endLocalDate
                );
    }

    private LinkedHashMap<LocalDate, String> getDayOfWeek(LocalDate startLocalDate, LocalDate endLocalDate) {
        LinkedHashMap<LocalDate, String> getDayOfWeek = new LinkedHashMap<>();
        for (LocalDate day = startLocalDate; !day.isAfter(endLocalDate); day = day.plusDays(1)) {
            getDayOfWeek.put(day, day.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
        return getDayOfWeek;
    }

    private List<LocalDate> getDaysOfPeriod(){
        List<LocalDate> days = new ArrayList<>();
        for(LocalDate day = this.startLocalDate; !day.isAfter(this.endLocalDate) ; day = day.plusDays(1)){
            days.add(day);
        }
        return days;
    }

    @PostMapping("/add-schedule")
    public String createSchedule(@RequestParam Integer shiftStartHour,
                                 @RequestParam Integer shiftStartMinutes,
                                 @RequestParam Integer shiftEndHour,
                                 @RequestParam Integer shiftEndMinutes,
                                 @RequestParam (defaultValue = "false", required = false) boolean isPresent,
                                 @RequestParam String note,
                                 Model model) {
        model.addAttribute("addScheduleClass", "openAddSchedule" );
        if(shiftStartMinutes == null){
            shiftStartMinutes = 0;
        }
        if(shiftEndMinutes == null){
            shiftEndMinutes = 0;
        }
        LocalDateTime shiftStart = this.selectedDay.atTime(shiftStartHour, shiftStartMinutes);
        LocalDateTime shiftEnd = this.selectedDay.atTime(shiftEndHour, shiftEndMinutes);
        Employee employee = employeesService.getEmployeeByUsername(this.selectedEmployee.getUsername());
        if(note.equals("")){
            note=null;
        }
        Schedule schedule = new Schedule(shiftStart, shiftEnd, isPresent, note);
        schedule.setEmployee(employee);
        if(scheduleService.isEmployeeDayExist(employee,shiftStart.toLocalDate())){
            Schedule existedSchedule = scheduleService.getScheduleByEmployeeDate(employee,shiftStart.toLocalDate());
            scheduleService.updateScheduleById(existedSchedule.getId(), shiftStart, shiftEnd, note);
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
                this.selectedDay.atTime(favoriteShift.getShiftStart()),
                this.selectedDay.atTime(favoriteShift.getShiftEnd()),
                true,
                null);
        schedule.setEmployee(employee);
        if(scheduleService.isEmployeeDayExist(employee, this.selectedMonth)){
            Schedule existedSchedule = scheduleService.getScheduleByEmployeeDate(employee,schedule.getShiftStart().toLocalDate());
            scheduleService.updateScheduleById(existedSchedule.getId(), schedule.getShiftStart(), schedule.getShiftStart(), schedule.getNotes());
        }else{
            scheduleService.saveSchedule(schedule);
        }
        this.scheduleFormClass ="hide";
        return "redirect:/schedule";
    }

    @GetMapping("/delete-employee-day-shift")
    public String deleteEmployeeDayShift() {
        Employee employee = employeesService.getEmployeeByUsername(this.selectedEmployee.getUsername());
        Schedule schedule = scheduleService.getScheduleByEmployeeDate(employee, this.selectedDay);
        if(schedule.getShiftStart() != null || schedule.getShiftEnd() != null){
            scheduleService.deleteSchedule(schedule);
        }
        this.scheduleFormClass = "hide";
        return "redirect:/schedule";
    }


    //select
    @GetMapping ("/select-employee/{employeeUsername}/select-day/{day}")
    public String selectEmployeeAndDayOfMonth(@PathVariable("employeeUsername")  String employeeUsername,
                                              @PathVariable("day") LocalDate day) {
        this.scheduleFormClass = "openAddSchedule";
        this.selectedEmployee = employeesService.getEmployeeByUsername(employeeUsername);
        this.selectedDay = day;
        this.selectedSchedule = scheduleService.getScheduleByEmployeeDate(this.selectedEmployee,this.selectedDay);
        return "redirect:/schedule";
    }


    @GetMapping("/month-list")
    public String showMonthList(@RequestParam(value = "month", required = false) String selectedMonth) {
        this.selectedMonth = LocalDate.parse(selectedMonth+"-01");
        this.startLocalDate = this.selectedMonth;
        this.endLocalDate = this.selectedMonth.withDayOfMonth(this.selectedMonth.lengthOfMonth());
        return "redirect:/schedule";
    }

    //set start localDate for employees schedule with last seven days of previous month
    @GetMapping("/get-last-seven-days-of-previous-selected-month")
    public String getLastWeekOfPreviousMont() {
        LocalDate previousMonth = this.selectedMonth.minusMonths(1);
        this.startLocalDate = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()-7);
        return "redirect:/schedule";
    }

    //set end localDate for employees schedule with first seven days of next month
    @GetMapping("/get-first-seven-days-of-next-selected-month")
    public String getFirstWeekOfNextMont() {
        LocalDate nextMonth = this.selectedMonth.plusMonths(1);
        this.endLocalDate = nextMonth.withDayOfMonth(7);
        return "redirect:/schedule";
    }

    //set start localDay for employees schedule with selected month first day.
    @GetMapping("/get-first-day-of-selected-month")
    public String getFisrtDayOfSelectedMonth() {
        this.startLocalDate = this.selectedMonth;
        return "redirect:/schedule";
    }

    //set end localDay for employees schedule with selected month last day.
    @GetMapping("/get-last-day-of-selected-month")
    public String getLastDayOfSelectedMonth() {
        this.endLocalDate = this.selectedMonth.withDayOfMonth(this.selectedMonth.lengthOfMonth());
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
    public String getScheduleFiltered(@RequestParam (required = false) String employeeFirstName,
                                      @RequestParam (required = false) String employeeLastName,
                                      @RequestParam (required = false) Separate employeeSeparate,
                                      @RequestParam (required = false) Position employeePosition) {
            List<Employee>filteredEmpolyeeList =
                    employeesService.getFilteredEmployeesListByFirstNameLastNameSeparatePosition(
                    employeeFirstName, employeeLastName, employeeSeparate, employeePosition);
            employeesService.setEmployeesList(filteredEmpolyeeList);

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
