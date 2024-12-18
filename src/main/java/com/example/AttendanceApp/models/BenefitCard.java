package com.example.AttendanceApp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity(name = "benefit_cards")
public class BenefitCard extends BaseEntity{
    @Column(name = "serial_number")
    private String serialNumber;
    private int points;
    @OneToOne(mappedBy = "benefitCard")
    private Employee employee;

    public BenefitCard() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "BenefitCard{" +
                "serialNumber='" + serialNumber + '\'' +
                ", points=" + points +
                ", employee=" + employee +
                '}';
    }
}
