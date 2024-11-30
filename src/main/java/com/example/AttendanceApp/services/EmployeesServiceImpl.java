package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Employees;
import com.example.AttendanceApp.repositaries.EmployeesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService{

    private final EmployeesRepository employeesRepository;

    public EmployeesServiceImpl(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Override
    public List<Employees> getEmployees() {
        return employeesRepository.findAll();
    }

    @Override
    public Employees getEmployeeById(long id) {
       return employeesRepository.findById(id).orElseThrow(
               () -> new IllegalArgumentException(String.format("Location with id (%s) not found.", id))
       );
    }

    @Override
    public void saveEmployee(Employees employee) {
        employeesRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Employees employee) {
        employeesRepository.delete(employee);
    }

    @Override
    public void updateEmployeeById(long id) {
        Optional<Employees> employees = employeesRepository.findById(id);
        if (employees.isPresent()){
            Employees e = employees.get();
        }
    }
}
