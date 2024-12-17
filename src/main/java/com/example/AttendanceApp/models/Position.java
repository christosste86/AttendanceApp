package com.example.AttendanceApp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;

    @OneToMany
    private List<Employee> employees = new ArrayList<>();

    public Position() {
    }

    public Position(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String position) {
        this.title = position;
    }

    public void setEmployees(Employee employee) {
        employees.add(employee);
        employee.setPosition(this);
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
