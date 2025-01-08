package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface EmployeesService {
    boolean isExist(String username);

    List<Employee> employeesListByRole();

    List<Employee> getEmployeesList();

    void setEmployeesList(List<Employee> employeesList);

    List<Employee> getFilteredEmployeesListByFirstNameLastNameSeparatePosition(String firstName, String lastName, Separate separate, Position position);

    List<Employee> getFilteredEmployeesListByFirstNameLastNameSeparate(String firstName, String lastName, Separate separate);

    List<Employee> getFilteredEmployeesListByFirstNameLastNamePosition(String firstName, String lastName, Position position);

    List<Employee> getFilteredEmployeesListByFirstNameLastName(String firstName, String lastName);

    Employee getEmployeeByUsername(String username);

    Employee getEmployeeById(long id);

    Employee getLoginEmployee();

    void saveEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    void deleteById(Long id);

    void updateEmployeeById(long id,
                            String firstName,
                            String lastName,
                            BenefitCard benefitCard,
                            Separate separate,
                            Position position,
                            Assignment assignment,
                            Double paymentPerHour,
                            String username,
                            String password);
}
