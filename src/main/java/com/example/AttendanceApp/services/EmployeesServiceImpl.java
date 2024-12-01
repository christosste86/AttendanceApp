package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Employee;
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
    public List<Employee> getEmployees() {
        return employeesRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
       return employeesRepository.findById(id).orElseThrow(
               () -> new IllegalArgumentException(String.format("Location with id (%s) not found.", id))
       );
    }

    @Override
    public boolean isExist(String firstName, String lastname){
        return employeesRepository.isExist(firstName, lastname);
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeesRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeesRepository.delete(employee);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Employee> employees = employeesRepository.findById(id);
        if (employees.isPresent()){
            employeesRepository.deleteById(id);
        }
    }

    @Override
    public void updateEmployeeById(long id) {
        Optional<Employee> employees = employeesRepository.findById(id);
        if (employees.isPresent()){
            Employee e = employees.get();
        }
    }
}
