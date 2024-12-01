package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Employee;

import java.util.List;

public interface EmployeesService {
    boolean isExist(String firstName, String lastname);

    List<Employee> getEmployees();

    Employee getEmployeeById(long id);

    void saveEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    void deleteById(Long id);

    void updateEmployeeById(long id);
}
