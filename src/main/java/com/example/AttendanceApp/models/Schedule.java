package com.example.AttendanceApp.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Schedule {
    private long id;
    private long employee_id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Double workedHours;
    private boolean isPresent;


}
