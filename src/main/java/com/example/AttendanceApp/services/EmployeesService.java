package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface EmployeesService {
    boolean isExist(String username);

    List<Employee> getEmployeesList();

    List<Employee> getFilteredEmployeesList(String firstName, String lastName, Separate separate, Position position);

    Employee getEmployeeByUsername(String username);

    Employee getEmployeeById(long id);

    void saveEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    void deleteById(Long id);

    void updateEmployeeById(long id,
                            String firstName,
                            String lastName,
                            Separate separate,
                            Position position,
                            Assignment assignment,
                            Double paymentPerHour,
                            String username,
                            String password);
}
