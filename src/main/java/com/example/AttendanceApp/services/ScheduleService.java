package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Details;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface ScheduleService {
    boolean scheduleExists(Employee employee, LocalDateTime shiftStart, LocalDateTime shiftEnd);

    List<Schedule> getSchedule();

    Schedule getScheduleByEmployeeDate(Employee employee, LocalDate shiftDay);

    void saveSchedule(Schedule schedule);

    boolean isEmployeeDayExist(Employee employee, LocalDate day);

    void deleteSchedule(Schedule schedule);

    void updateScheduleById(long id, LocalDateTime shiftStart, LocalDateTime shiftEnd);

    LinkedHashMap<Employee, List<Schedule>> employeesScheduleHashMapPerMonth(List<Employee> employees, LocalDate startLocalDate, LocalDate endLocalDate);

    HashMap<Employee, Details> monthlyTotalHours(LocalDate month, HashMap<Employee, List<Schedule>> employeesScheduleHashMapPerMonth);

    Double monthlyFullTimeHours(LocalDate month, int assignment);

}
