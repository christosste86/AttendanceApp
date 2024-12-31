package com.example.AttendanceApp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity (name = "positions")
public class Position extends BaseEntity{


    private String title;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "position")
    private List<Employee> employees = new ArrayList<>();

    public Position() {
    }

    public Position(String title, Role role) {
        this.title = title;
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String position) {
        this.title = position;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmployees(Employee employee) {
        employees.add(employee);
        employee.setPosition(this);
    }

    @Override
    public String toString() {
        return "Position{" +
                ", title='" + title + '\'' +
                '}';
    }
}
