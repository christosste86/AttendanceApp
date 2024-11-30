package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Employees;

import java.util.List;

public interface EmployeesService {
    List<Employees> getEmployees();

    Employees getEmployeeById(long id);

    void saveEmployee(Employees employee);

    void deleteEmployee(Employees employee);

    void updateEmployeeById(long id);
}
