package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.repositaries.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService{

    private final EmployeeRepository employeesRepository;

    public EmployeesServiceImpl(EmployeeRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }


    @Override
    public List<Employee> getEmployeesList(String firstName,
                                           String lastName,
                                           Separate separate,
                                           Position position) {
        List<Employee> filteredList = employeesRepository.filterEmployees(firstName, lastName, separate, position);
        if(filteredList.isEmpty()){
            return employeesRepository.findAll();
        }
        return filteredList;
    }

    @Override
    public Employee getEmployeeById(long id) {
       return employeesRepository.findById(id).orElseThrow(
               () -> new IllegalArgumentException(String.format("Location with id (%s) not found.", id))
       );
    }

    @Override
    public boolean isExist(String username){
        return !employeesRepository.findEmployeesByUsername(username).isEmpty();
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

    @Override
    public List<Employee> filteredEmployees(String firstName, String lastName, Separate separate, Position position){
        if(employeesRepository.filterEmployees(firstName, lastName, separate, position).isEmpty()){
            return employeesRepository.findAll();
        }else{
            return employeesRepository.filterEmployees(firstName, lastName, separate, position);
        }
    }
}
