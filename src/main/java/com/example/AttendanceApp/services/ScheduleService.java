package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Details;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface ScheduleService {
    boolean scheduleExists(Employee employee, LocalDateTime shiftStart, LocalDateTime shiftEnd);

    List<Schedule> getSchedule();

    Schedule getScheduleById(long id);

    void saveSchedule(Schedule schedule);

    void deleteSchedule(Schedule schedule);

    void updateScheduleById(long id);

    List<Schedule>monthlyEmployeesSchedule(LocalDate month);

    HashMap<Employee, List<Schedule>> employeesScheduleHashMapPerMonth(LocalDate month, List<Employee> employees);

    HashMap<Employee, Details> monthlyTotalHours(LocalDate month, HashMap<Employee, List<Schedule>> employeesScheduleHashMapPerMonth);

    Double monthlyFullTimeHours(LocalDate month, int assignment);

}
