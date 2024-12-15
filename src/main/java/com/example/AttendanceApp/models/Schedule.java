package com.example.AttendanceApp.models;

import jakarta.persistence.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Entity
@Table(name ="Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    @Column (name ="worked_hours")
    private Double workedHours;
    private boolean isPresent;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    public Schedule() {
    }

    public Schedule(LocalDateTime start, LocalDateTime end, Double workedHours, boolean isPresent) {
        this.shiftStart = start;
        this.shiftEnd = end;
        this.workedHours = workedHours;
        this.isPresent = isPresent;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public long getId() {
        return id;
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
                ", shiftStart=" + shiftStart +
                ", shiftEnd=" + shiftEnd +
                ", workedHours=" + workedHours +
                ", isPresent=" + isPresent +
                '}';
    }
}
