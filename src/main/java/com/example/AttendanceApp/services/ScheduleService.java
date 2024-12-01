package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    boolean scheduleExists(long employeeId, LocalDateTime shiftStart, LocalDateTime shiftEnd, Double workedHours, boolean isPresent);

    List<Schedule> getSchedule();

    Schedule getScheduleById(long id);

    void saveSchedule(Schedule schedule);

    void deleteSchedule(Schedule schedule);

    void updateScheduleById(long id);
}
