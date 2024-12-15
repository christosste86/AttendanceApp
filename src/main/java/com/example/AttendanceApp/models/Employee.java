package com.example.AttendanceApp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    private String separatedName;
    private String position;
    private Integer assignment;
    private Double paymentPerHour;

    @OneToMany(mappedBy = "employee")
    private List<Schedule> schedules = new ArrayList<>();


    public Employee(String firstName, String lastName, String separatedName, String position, Integer assignment, Double paymentPerHour) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.separatedName = separatedName;
        this.position = position;
        this.assignment = assignment;
        this.paymentPerHour = paymentPerHour;
    }

    public Employee() {
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSeparatedName() {
        return separatedName;
    }

    public String getPosition() {
        return position;
    }

    public int getAssignment() {
        return assignment;
    }

    public double getPaymentPerHour() {
        return paymentPerHour;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Schedule schedule) {
        schedules.add(schedule);
        schedule.setEmployee(this);
    }

    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    @Override
    public String toString() {
        return "Employees{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
