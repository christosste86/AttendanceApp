package com.example.AttendanceApp.models;

public class Details {
    private Double totalHours;
    private Integer shifts;
    private Double monthlyFullTimeHours;

    public Details(Double totalHours, Integer shifts, Double monthlyFullTimeHours) {
        this.totalHours = totalHours;
        this.shifts = shifts;
        this.monthlyFullTimeHours = monthlyFullTimeHours;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public Integer getShifts() {
        return shifts;
    }

    public Double getMonthlyFullTimeHours() {
        return monthlyFullTimeHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public void setShifts(Integer shifts) {
        this.shifts = shifts;
    }

    public void setMonthlyFullTimeHours(Double monthlyFullTimeHours) {
        this.monthlyFullTimeHours = monthlyFullTimeHours;
    }
}
