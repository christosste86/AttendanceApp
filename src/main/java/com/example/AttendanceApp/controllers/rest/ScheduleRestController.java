package com.example.AttendanceApp.controllers.rest;

import com.example.AttendanceApp.models.Details;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Schedule;
import com.example.AttendanceApp.models.dtos.ScheduleDTO;
import com.example.AttendanceApp.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin(origins = "http://localhost:8081")
public class ScheduleRestController {
    //services
    private final ScheduleService scheduleService;
    private final PositionService positionService;
    private final SeparateService separateService;
    private final EmployeesService employeesService;
    private final FavoriteShiftService favoriteShiftService;

    public ScheduleRestController(ScheduleService scheduleService, PositionService positionService, SeparateService separateService, EmployeesService employeesService, FavoriteShiftService favoriteShiftService) {
        this.scheduleService = scheduleService;
        this.positionService = positionService;
        this.separateService = separateService;
        this.employeesService = employeesService;
        this.favoriteShiftService = favoriteShiftService;
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return new ResponseEntity<>(scheduleService.getSchedules(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        if (schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }


    @GetMapping("/period-by-employee/{employeeId},{startPeriod}-{endPeriod}")
    public ResponseEntity<List<Schedule>> getAllSchedules(@PathVariable Long employeeId,
                                                          @PathVariable LocalDate startPeriod,
                                                          @PathVariable LocalDate endPeriod) {
        Employee employee = employeesService.getEmployeeById(employeeId);
        List<Schedule> scheduleList = scheduleService.getScheduleByEmployeeBetweenDays(employee, startPeriod, endPeriod);
        if(scheduleList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(scheduleList, HttpStatus.OK);
        }
    }
//    @GetMapping("{/period-delails-for-employee}/{employeeId},{startPeriod}-{endPeriod}")
//    public ResponseEntity<Details> getDetailsForEmployee(@PathVariable Long employeeId,
//                                                         @PathVariable LocalDate startPeriod,
//                                                         @PathVariable LocalDate endPeriod){
//
//    }
}
