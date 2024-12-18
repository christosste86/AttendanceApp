package com.example.AttendanceApp.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Double paymentPerHour;

    @ManyToOne
    @JoinColumn(name = "Separate_id", referencedColumnName = "id")
    Separate separate;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    Position position;

    @ManyToOne
    @JoinColumn(name = "assignment_id", referencedColumnName = "id")
    Assignment assignment;

    @OneToMany(mappedBy = "employee")
    private List<Schedule> schedules = new ArrayList<>();


    public Employee(String firstName, String lastName, String username, String password, Double paymentPerHour) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.paymentPerHour = paymentPerHour;
    }

    public Employee() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Double getPaymentPerHour() {
        return paymentPerHour;
    }

    public Separate getSeparate() {
        return separate;
    }

    public Position getPosition() {
        return position;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPaymentPerHour(Double paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }

    public void setSeparate(Separate separate) {
        this.separate = separate;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    @Override
    public String toString() {
        return "Employees{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
