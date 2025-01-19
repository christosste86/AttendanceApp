package com.example.AttendanceApp.models.dtos;

import com.example.AttendanceApp.models.Employee;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class ScheduleDTO {
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private boolean isPresent;
    private String notes;

    public ScheduleDTO(LocalDateTime shiftStart, LocalDateTime shiftEnd, boolean isPresent, String notes) {
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.isPresent = isPresent;
        this.notes = notes;
    }

    public LocalDateTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalDateTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalDateTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(LocalDateTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
