package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.repositaries.EmployeeRepository;
import com.example.AttendanceApp.security.util.SecurityUtil;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService, UserDetailsService {

    private final EmployeeRepository employeesRepository;
    private SecurityUtil securityUtil;

    public EmployeesServiceImpl(EmployeeRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }


    @Override
    public List<Employee> getEmployeesList() {
        if(!getLoginEmployee().getRoles().stream().filter(role -> role.equals("ROLE_LEVEL3")).toList().isEmpty()){
            return employeesRepository.findEmployeesByUsername(getLoginEmployee().getUsername());
        }
        if(!getLoginEmployee().getRoles().stream().filter(role -> role.equals("ROLE_LEVEL2")).toList().isEmpty()){
            return employeesRepository.findEmployeesBySeparate(getLoginEmployee().getSeparate());
        }
        if(!getLoginEmployee().getRoles().stream().filter(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_LEVEL1")).toList().isEmpty()){
            return employeesRepository.findAll();
        }return null;
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
    public Employee getLoginEmployee() {
        return securityUtil.getLoginUser();
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeesRepository.findEmployeeByUsername(username);
        if (employee.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return employee.get();
    }
}
