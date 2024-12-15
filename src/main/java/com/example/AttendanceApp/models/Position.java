package com.example.AttendanceApp.models;

public enum Position {
    MANAGER("Manager"),
    LEADER("Leader"),
    WORKER("Worker");

    private final String title;

    Position(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
