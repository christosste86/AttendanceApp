package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Details;
import com.example.AttendanceApp.models.Employees;
import com.example.AttendanceApp.models.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface ScheduleService {
    boolean scheduleExists(long employeeId, LocalDateTime shiftStart, LocalDateTime shiftEnd, Double workedHours, boolean isPresent);

    List<Schedule> getSchedule();

    Schedule getScheduleById(long id);

    void saveSchedule(Schedule schedule);

    void deleteSchedule(Schedule schedule);

    void updateScheduleById(long id);

    List<Schedule>monthlyEmployeesSchedule(LocalDate month);

    HashMap<Employees, List<Schedule>> employeesScheduleHashMapPerMonth(LocalDate month, List<Employees> employees);

    HashMap<Employees, Details> monthlyTotalHours(LocalDate month, HashMap<Employees, List<Schedule>> employeesScheduleHashMapPerMonth);

    Double monthlyFullTimeHours(LocalDate month, int assignment);
}
