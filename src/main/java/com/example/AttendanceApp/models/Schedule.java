package com.example.AttendanceApp.models;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name ="Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name ="employee_id")
    private long employeeId;
    private LocalDateTime start;
    private LocalDateTime end;
    @Column (name ="woked_hours")
    private Double workedHours;
    private boolean isPresent;

    public Schedule() {
    }

    public Schedule(long employeeId, LocalDateTime start, LocalDateTime end, Double workedHours, boolean isPresent) {
        this.employeeId = employeeId;
        this.start = start;
        this.end = end;
        this.workedHours = workedHours;
        this.isPresent = isPresent;
    }
}
