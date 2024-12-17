package com.example.AttendanceApp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "assignment_title")
    private String assignmentTitle;
    @Column(name = "assignment")
    private int hoursPerWeek;

    @OneToMany(mappedBy = "assignment")
    private List<Employee> employees = new ArrayList<>();

    public Assignment() {
    }

    public Assignment(String assignmentTitle, int hoursPerWeek) {
        this.assignmentTitle = assignmentTitle;
        this.hoursPerWeek = hoursPerWeek;
    }

    public void setEmployees(Employee employee) {
        employees.add(employee);
        employee.setAssignment(this);
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", assignmentTitle='" + assignmentTitle + '\'' +
                ", hoursPerWeek=" + hoursPerWeek +
                '}';
    }
}
