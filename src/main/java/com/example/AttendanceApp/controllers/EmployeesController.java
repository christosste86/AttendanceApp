package com.example.AttendanceApp.controllers;


import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeesController {

    private final EmployeesService employeesService;
    private List<Employee> employeesList = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String separatedName;
    private String position;

    @Autowired
    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
        this.employeesList = employeesService.getEmployees();
    }

    public List<Employee> getEmployeesList(){
        return this.employeesList;
    }

    @GetMapping("/employees")
    public String employeesPage(Model model){
        model.addAttribute("separatedList", employeesService.getSeparates());
        model.addAttribute("positionsList", employeesService.getPositions());
        model.addAttribute("assignmentsList", employeesService.getAssignments());
        model.addAttribute("employees", employeesService.getEmployees());
        return "employees";
    }

    @GetMapping("/add-employee")
    public String getEmployeesForm(){
        return "employees";
    }

    @PostMapping("/add-employee")
    public String createEmployee(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String separatedName,
                                 @RequestParam String position,
                                 @RequestParam Integer assignment,
                                 @RequestParam Double paymentPerHour){
        System.out.println("Received Data: First Name = " + firstName + ", Last Name = " + lastName);
        Employee employee = new Employee(firstName, lastName, separatedName, position, assignment, paymentPerHour);
        if(employeesService.isExist(firstName, lastName)){
            employeesService.saveEmployee(employee);
        }
        return "redirect:/employees";
    }

    @GetMapping("/employee-filter/")
    public String getEmployeeFilter(Model model, @PathVariable("firstName") String firstName,
                                    @PathVariable("lastName") String lastName,
                                    @PathVariable("seperatedName") String seperatedName,
                                    @PathVariable("position") String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.separatedName = seperatedName;
        this.position = position;
        this.employeesList = employeesService.filteredEmployees(this.firstName, this.lastName, this.separatedName, this.position);
        model.addAttribute("filteredEmployees", this.employeesList);
        return "redirect:/";
    }

    @GetMapping("/about-employee/{employee}")
    public String aboutEmployeePage(Model model, @PathVariable("employee") Long employeeOrder){
        model.addAttribute("employees", employeesService.getEmployeeById(employeeOrder));
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable("id") Long employeeOrder){
        employeesService.deleteById(employeeOrder);
        return "redirect:/employees";
    }
}
