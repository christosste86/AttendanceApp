package com.example.AttendanceApp.models.dtos;

public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Double paymentPerHour;

    public EmployeeDTO(String firstName, String lastName, String username, String password, Double paymentPerHour) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.paymentPerHour = paymentPerHour;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getPaymentPerHour() {
        return paymentPerHour;
    }

    public void setPaymentPerHour(Double paymentPerHour) {
        this.paymentPerHour = paymentPerHour;
    }
}
