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
import java.util.Map;

public interface ScheduleService {
    List<Schedule> getSchedule();

    Schedule getScheduleByEmployeeDate(Employee employee, LocalDate shiftDay);

    void saveSchedule(Schedule schedule);

    boolean isEmployeeDayExist(Employee employee, LocalDate day);

    void deleteSchedule(Schedule schedule);

    void updateScheduleById(long id, LocalDateTime shiftStart, LocalDateTime shiftEnd, String note);

    LinkedHashMap<Employee, LinkedHashMap<LocalDate, Schedule>> getEmployeesScheduleForPeriod(List<Employee> employees, LocalDate startLocalDate, LocalDate endLocalDate);

    LinkedHashMap<Employee, Details> getPeriodDetailsPerEmployee(List<Employee> employees, LocalDate startPeriod, LocalDate endPeriod);

    List<Schedule> getScheduleByEmployeeBetweenDays(Employee employee, LocalDate startDate, LocalDate endDate);

}
