package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.repositaries.EmployeesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<Employee> employees = employeesRepository.findAll();
        if(employees.stream().filter(e-> Objects.equals(e.getFirstName(), firstName) && Objects.equals(e.getLastName(), lastname)).toList().isEmpty()){
            return true;
        }return false;
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
    public List<Employee> filteredEmployees(String firstName, String lastName, String seperatedName, String position){
        if(employeesRepository.filterEmployees(firstName, lastName, seperatedName, position).isEmpty()){
            return employeesRepository.findAll();
        }else{
            return employeesRepository.filterEmployees(firstName, lastName, seperatedName, position);
        }
    }

    @Override
    public List<Assignment> getAssignments() {
        return Stream.of(Assignment.values()).collect(Collectors.toList());
    }

    @Override
    public List<Position> getPositions() {
        return Stream.of(Position.values()).collect(Collectors.toList());
    }

    @Override
    public List<Separate> getSeparates() {
        return Stream.of(Separate.values()).collect(Collectors.toList());
    }
}
