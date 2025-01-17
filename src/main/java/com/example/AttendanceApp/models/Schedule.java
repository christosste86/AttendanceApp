package com.example.AttendanceApp.models;

import jakarta.persistence.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Entity(name ="Schedules")
public class Schedule extends BaseEntity{
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    @Column (name ="worked_hours")
    private Double workedHours;
    private boolean isPresent;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    public Schedule() {
    }

    public Schedule(LocalDateTime start, LocalDateTime end, boolean isPresent, String notes) {
        this.shiftStart = start;
        this.shiftEnd = end;
        this.isPresent = isPresent;
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public void setShiftStart(LocalDateTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public void setShiftEnd(LocalDateTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public void setWorkedHours(Double workedHours) {
        this.workedHours = workedHours;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public String getEmptyDailySchedule(){
        return "";
    }

    public String getDailySchedule(){
        DecimalFormat df = new DecimalFormat("00");
        return String.format("%s:%s %s:%s",df.format(this.shiftStart.getHour())
                , df.format(this.shiftStart.getMinute())
                , df.format(this.shiftEnd.getHour())
                , df.format(this.shiftEnd.getMinute()));
    }

    public String getDayOfWeekShortEnText(){
        return this.shiftStart.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                ", shiftStart=" + shiftStart +
                ", shiftEnd=" + shiftEnd +
                ", workedHours=" + workedHours +
                ", isPresent=" + isPresent +
                '}';
    }
}
