package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
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
    public List<Employee> getEmployeesList() {
            return employeesRepository.findAll();
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        if(employeesRepository.findEmployeesByUsername(username).isEmpty()){
            return null;
        }
        return employeesRepository.findEmployeesByUsername(username).getFirst();
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
    public void updateEmployeeById(long id,
                                   String firstName,
                                   String lastName,
                                   Separate separate,
                                   Position position,
                                   Assignment assignment,
                                   Double paymentPerHour,
                                   String username,
                                   String password) {
        Optional<Employee> employees = employeesRepository.findById(id);
        if (employees.isPresent()){
            Employee e = employees.get();
            e.setFirstName(firstName);
            e.setLastName(lastName);
            e.setSeparate(separate);
            e.setPosition(position);
            e.setAssignment(assignment);
            e.setPaymentPerHour(paymentPerHour);
            e.setUsername(username);
            e.setPassword(password);
            employeesRepository.save(e);
        }
    }

    @Override
    public List<Employee> getFilteredEmployeesList(String firstName, String lastName, Separate separate, Position position){
        if(employeesRepository.filterEmployees(firstName, lastName, separate, position).isEmpty()){
            return employeesRepository.findAll();
        }else{
            return employeesRepository.filterEmployees(firstName, lastName, separate, position);
        }
    }
}
