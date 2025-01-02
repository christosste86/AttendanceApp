package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final EmployeesService employeesService;
    private Employee loginUser;

    @Autowired
    public HomeController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping("/")
    public String homePage(Model model){
        this.loginUser = employeesService.getLoginEmployee();
        model.addAttribute("loginUser", this.loginUser);
        return "index";
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String password){
        this.loginUser.setPassword(password);
        return "redirect:/";
    }
}
