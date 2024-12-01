package com.example.AttendanceApp.controllers;


import com.example.AttendanceApp.models.Employees;
import com.example.AttendanceApp.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;

@Controller
public class EmployeesController {

    private final EmployeesService employeesService;

    @Autowired
    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping("/employees")
    public String employeesPage(Model model){
        model.addAttribute("employees", employeesService.getEmployees());
        return "employees";
    }

    @GetMapping("/add-employee")
    public String getEmployeesForm(){
        return "add-employee";
    }

    @PostMapping("/add-employee")
    public String createEmployee(@RequestParam String firstName,
                                 @RequestParam String lastName){
        Employees employee = new Employees(firstName, lastName);
        if(employeesService.isExist(firstName, lastName)){
            employeesService.saveEmployee(employee);
        }
        return "redirect:/employees";
    }

    @GetMapping("/about-employee/{employee}")
    public String aboutEmployeePage(Model model, @PathVariable("employee") Long employeeOrder){
        model.addAttribute("employees", employeesService.getEmployeeById(employeeOrder));
        return "about-employee";
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable("id") Long employeeOrder){
        employeesService.deleteById(employeeOrder);
        return "redirect:/employees";
    }
}
