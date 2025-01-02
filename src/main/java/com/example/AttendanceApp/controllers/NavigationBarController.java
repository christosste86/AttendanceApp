package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class NavigationBarController {
    private final EmployeesService employeesService;
    private Employee loginUser;

    @Autowired
    public NavigationBarController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @ModelAttribute("loginUser")
    public Employee getLoginUser() {
        return employeesService.getLoginEmployee();
    }
}
