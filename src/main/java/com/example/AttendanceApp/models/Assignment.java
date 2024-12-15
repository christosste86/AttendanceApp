package com.example.AttendanceApp.models;

public enum Assignment {
    FULL_TIME(40),
    THREE_QUARTERS_TIME(30),
    HALF_TIME(20),
    QUARTER_TIME(15);

    private final int hoursPerWeek;

    Assignment(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

}
