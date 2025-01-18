package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.*;
import com.example.AttendanceApp.repositaries.EmployeeRepository;
import com.example.AttendanceApp.repositaries.RoleRepository;
import com.example.AttendanceApp.security.util.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private Employee logintEmployee;
    private List<Employee> employeesList = new ArrayList<>();

    public EmployeesServiceImpl(EmployeeRepository employeesRepository) {
        this.employeesRepository = employeesRepository;

    }

    private List<Employee> getEmployeesWithRoleLevelOneAndTwo(){
        List<Employee> employees = new ArrayList<>();
        employeesRepository.findEmployeesBySeparateOrderByPositionTitle(getLoginEmployee().getSeparate()).forEach(employee -> {
            employee.getRoles().forEach(r->{
                if(r.getName().equals("ROLE_LEVEL3") || r.getName().equals("ROLE_LEVEL2")){
                    employees.add(employee);
                }
            });
        });
        return employees;
    }



    @Override
    public List<Employee> employeesListByRole() {
        String role = getLoginEmployee().getRoles().stream().toList().getFirst().toString();
        if(role.equals("ROLE_LEVEL3")){
            return List.of(getEmployeeByUsername(getLoginEmployee().getUsername()));
        }
        if(role.equals("ROLE_LEVEL2")){
            return getEmployeesWithRoleLevelOneAndTwo();
        }
        if(role.equals("ROLE_ADMIN") || role.equals("ROLE_LEVEL1")){
            return employeesRepository.findEmployeesOrderByPositionTitle();
        }return new ArrayList<>();
    }

    @Override
    public List<Employee> getEmployeesList() {
        return this.employeesList;
    }

    @Override
    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        Optional<Employee> employee = employeesRepository.findEmployeeByUsername(username);
        if(employee.isPresent()){
            return employee.get();
        }return null;
    }

    @Override
    public Employee getEmployeeById(long id) {
       return employeesRepository.findById(id).orElseThrow(
               () -> new IllegalArgumentException(String.format("Location with id (%s) not found.", id))
       );
    }

    @Override
    public Employee getLoginEmployee() {
        return this.logintEmployee;
    }

    @Override
    public boolean isExist(String username){
        Optional<Employee> employee = employeesRepository.findEmployeeByUsername(username);
        return employee.isPresent();
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
                                   BenefitCard benefitCard,
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
            e.setBenefitCard(benefitCard);
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
    public List<Employee> getFilteredEmployeesListByFirstNameLastNameSeparatePosition(String firstName, String lastName, Separate separate, Position position){
        return employeesRepository.filterEmployeesByFirstNameLastNameSeparatePosition("%" + firstName + "%","%" + lastName + "%", separate, position);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeesRepository.findEmployeeByUsername(username);
        if (employee.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        this.logintEmployee = employee.get();
        this.employeesList = employeesListByRole();
        return this.logintEmployee;
    }
}
