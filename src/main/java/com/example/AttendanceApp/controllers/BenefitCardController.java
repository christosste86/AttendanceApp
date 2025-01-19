package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.BenefitCard;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.services.BenefitCardService;
import com.example.AttendanceApp.services.EmployeesService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BenefitCardController {
    private final BenefitCardService benefitCardService;
    private final EmployeesService employeesService;

    public BenefitCardController(BenefitCardService benefitCardService, EmployeesService employeesService) {
        this.benefitCardService = benefitCardService;
        this.employeesService = employeesService;
    }

    @GetMapping("/benefit-card/{employeeId}")
    public String benefitCardEmployeePage(Model model, @PathVariable("employeeId") Long employeeOrder){
        Employee employee = employeesService.getEmployeeById(employeeOrder);
        model.addAttribute("employee", employee);
        model.addAttribute("employeeBenefitCard", employee.getBenefitCard());
        model.addAttribute("loginUser", employeesService.getLoginEmployee());
        return "benefit-card";
    }

    @PostMapping("/benefit-card/{employeeId}/add-credit")
    public String addCredit(@PathVariable Long employeeId,
                            @RequestParam int points){
        Employee employee = employeesService.getEmployeeById(employeeId);
        BenefitCard benefitCard = employee.getBenefitCard();
        int benefitCardPoints = benefitCard.getPoints();
        benefitCard.setPoints(benefitCardPoints + points);
        benefitCardService.updateBenefitCardPoints(benefitCard);
        return "redirect:/benefit-card/"+ employeeId;
    }

    @GetMapping("/benefit-card/{employeeId}/delete-benefit-card")
    public String deleteBenefitCard(@PathVariable Long employeeId){
        Employee employee = employeesService.getEmployeeById(employeeId);
        employee.setBenefitCard(null);
        benefitCardService.deleteBenefitCard(employeeId);
        return "redirect:/benefit-card/"+ employeeId;
    }
}
