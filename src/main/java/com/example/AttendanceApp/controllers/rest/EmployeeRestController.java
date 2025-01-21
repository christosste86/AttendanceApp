package com.example.AttendanceApp.controllers.rest;

import com.example.AttendanceApp.models.*;
import com.example.AttendanceApp.models.dtos.EmployeeDTO;
import com.example.AttendanceApp.models.dtos.ScheduleDTO;
import com.example.AttendanceApp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeRestController {
    private final EmployeesService employeesService;
    private final SeparateService separateService;
    private final PositionService positionService;
    private final AssignmentService assignmentService;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleService scheduleService;
    private final FavoriteShiftService favoriteShiftService;


    @Autowired
    public EmployeeRestController(EmployeesService employeesService, SeparateService separateService, PositionService positionService, AssignmentService assignmentService, PasswordEncoder passwordEncoder, ScheduleService scheduleService, FavoriteShiftService favoriteShiftService) {
        this.employeesService = employeesService;
        this.separateService = separateService;
        this.positionService = positionService;
        this.assignmentService = assignmentService;
        this.passwordEncoder = passwordEncoder;
        this.scheduleService = scheduleService;
        this.favoriteShiftService = favoriteShiftService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeesService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Employee employee = employeesService.getEmployeeById(id);
        return employee != null ? new ResponseEntity<>(employee, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getUsername(),
                passwordEncoder.encode(employeeDTO.getPassword()),
                employeeDTO.getPaymentPerHour());
        employeesService.saveEmployee(employee);
        return new ResponseEntity<>("Employee created successfully", HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id,
                                                 @RequestParam String firstName,
                                                 @RequestParam String lastName,
                                                 @RequestParam BenefitCard benefitCardSn,
                                                 @RequestParam Separate separate,
                                                 @RequestParam Position position,
                                                 @RequestParam Assignment assignment,
                                                 @RequestParam Double paymentPerHour,
                                                 @RequestParam String username,
                                                 @RequestParam String password) {
        employeesService.updateEmployeeById(
                id,
                firstName,
                lastName,
                benefitCardSn,
                separate,
                position,
                assignment,
                paymentPerHour,
                username,
                passwordEncoder.encode(password));
        return new ResponseEntity<>("Bird weight increased successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeesService.deleteById(id);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/{employeeId}/add-assigment")
    public ResponseEntity<String> addAssigmentToEmployee(@PathVariable Long employeeId, @RequestParam Long assigmentId) {
        Employee employee = employeesService.getEmployeeById(employeeId);
        Assignment assignment = assignmentService.getAssignmentById(assigmentId);

        if (employee == null || assignment == null) {
            return new ResponseEntity<>("Employee or Assignment not found", HttpStatus.NOT_FOUND);
        }

        if (!employee.getAssignment().equals(assignment)) {
            employee.setAssignment(assignment);
            employeesService.saveEmployee(employee);
            return new ResponseEntity<>("Assigment added to employee successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Assigment already exists for this employee", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/{employeeId}/add-position")
    public ResponseEntity<String> addPositionToEmployee(@PathVariable Long employeeId, @RequestParam Long positionId) {
        Employee employee = employeesService.getEmployeeById(employeeId);
        Position position = positionService.getPositionById(positionId);
        if (employee == null || position == null) {
            return new ResponseEntity<>("Employee or Position not found", HttpStatus.NOT_FOUND);
        }
        if (!employee.getPosition().equals(position)) {
            employee.setPosition(position);
            //add roles to employee
            employeesService.saveEmployee(employee);
            return new ResponseEntity<>("Position added to employee successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Position already exists for this employee", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/{employeeId}/add-separate")
    public ResponseEntity<String> addSeparateToEmployee(@PathVariable Long employeeId,
                                                        @RequestParam Long separateId) {
        Employee employee = employeesService.getEmployeeById(employeeId);
        Separate separate = separateService.getSeparateById(separateId);
        if (employee == null || separate == null) {
            return new ResponseEntity<>("Employee or Separate not found", HttpStatus.NOT_FOUND);
        }
        if (!employee.getSeparate().equals(separate)) {
            employee.setSeparate(separate);
            employeesService.saveEmployee(employee);
            return new ResponseEntity<>("Separate added to employee successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Separate already exists for this employee", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("{employeeId}/create-update-schedule")
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleDTO scheduleDTO,
                                                   @PathVariable Long employeeId) {
        Employee employee = employeesService.getEmployeeById(employeeId);
        Schedule schedule = new Schedule(
                scheduleDTO.getShiftStart(),
                scheduleDTO.getShiftEnd(),
                scheduleDTO.isPresent(),
                scheduleDTO.getNotes());
        schedule.setEmployee(employee);
        favoriteShiftService.createFavoriteByTimesShift(schedule);
        if(scheduleService.isEmployeeDayExist(employee, scheduleDTO.getShiftStart().toLocalDate())){
            Schedule existedSchedule = scheduleService.getScheduleByEmployeeDate(employee, scheduleDTO.getShiftStart().toLocalDate());
            scheduleService.deleteSchedule(existedSchedule);
            scheduleService.saveSchedule(schedule);
            return new ResponseEntity<>("Schedule updated successfully", HttpStatus.CREATED);
        }else{
            scheduleService.saveSchedule(schedule);
            return new ResponseEntity<>("Schedule created successfully", HttpStatus.CREATED);
        }
    }

    @GetMapping("{employeeId}/get-schedules-by-period/{localDayStar}-{localDayEnd}")
    public ResponseEntity<List<Schedule>> getScheduleByPeriod(@PathVariable Long employeeId,
                                                              @PathVariable LocalDate localDayStar,
                                                              @PathVariable LocalDate localDayEnd) {
        Employee employee = employeesService.getEmployeeById(employeeId);
        return new ResponseEntity<>(scheduleService.getScheduleByEmployeeBetweenDays(employee, localDayStar, localDayEnd), HttpStatus.OK);
    }

}
