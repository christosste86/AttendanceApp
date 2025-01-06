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

    @GetMapping("/benefits")
    public String benefitsPage(Model model) {
        model.addAttribute("benefitCardsList", benefitCardService.getBenefitCards());
        return "benefit-card";
    }

    @GetMapping("/benefit-card/{employee}")
    public String benefitCardEmployeePage(Model model, @PathVariable("employee") Long employeeOrder){
        model.addAttribute("employee", employeesService.getEmployeeById(employeeOrder));
        return "benefit-card";
    }


    @PostMapping("/benefit-card/{employeeId}/add-credit")
    public String addCredit(@PathVariable Long employeeId,
                            @ModelAttribute int points){
        Employee employee = employeesService.getEmployeeById(employeeId);
        BenefitCard benefitCard = employee.getBenefitCard();
        int benefitCardPoints = benefitCard.getPoints();

        benefitCard.setPoints(points);
        benefitCardService.updateBenefitCardPoints(benefitCard);
        return "redirect:/benefit-card/"+ employeeId;
    }
}
