package com.example.AttendanceApp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity (name = "positions")
public class Position extends BaseEntity{


    private String title;

    private String sortTitle;

    private String role;

    @OneToMany(mappedBy = "position")
    private List<Employee> employees = new ArrayList<>();

    public Position() {
    }

    public Position(String title,String sortTitle, String role) {
        this.title = title;
        this.sortTitle = sortTitle;
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String position) {
        this.title = position;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSortTitle() {
        return sortTitle;
    }

    public void setSortTitle(String sortTitle) {
        this.sortTitle = sortTitle;
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
