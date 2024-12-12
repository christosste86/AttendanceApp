package com.example.AttendanceApp.models;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.text.DecimalFormat;
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
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    @Column (name ="worked_hours")
    private Double workedHours;
    private boolean isPresent;

    public Schedule() {
    }

    public Schedule(long employeeId, LocalDateTime start, LocalDateTime end, Double workedHours, boolean isPresent) {
        this.employeeId = employeeId;
        this.shiftStart = start;
        this.shiftEnd = end;
        this.workedHours = workedHours;
        this.isPresent = isPresent;
    }

    public long getId() {
        return id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getShiftStart() {
        return shiftStart;
    }

    public LocalDateTime getShiftEnd() {
        return shiftEnd;
    }

    public Double getWorkedHours() {
        return workedHours;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public String getDailySchedule(){
        DecimalFormat df = new DecimalFormat("00");
        return String.format("%s:%s %s:%s",df.format(this.shiftStart.getHour())
                , df.format(this.shiftStart.getMinute())
                , df.format(this.shiftEnd.getHour())
                , df.format(this.shiftEnd.getMinute()));
    }

    public String getEmptyDailySchedule(){
        return "";
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", shiftStart=" + shiftStart +
                ", shiftEnd=" + shiftEnd +
                ", workedHours=" + workedHours +
                ", isPresent=" + isPresent +
                '}';
    }
}
