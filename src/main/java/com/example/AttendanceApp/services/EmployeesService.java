package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;

import java.util.List;

public interface EmployeesService {
    boolean isExist(String firstName, String lastname);

    List<Employee> getEmployees();

    Employee getEmployeeById(long id);

    void saveEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    void deleteById(Long id);

    void updateEmployeeById(long id);

    List<Employee> filteredEmployees(String firstName, String lastName, String seperatedName, String position);

    List<Assignment> getAssignments();

    List<Position> getPositions();

    List<Separate> getSeparates();
}
