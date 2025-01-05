package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.BenefitCard;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.services.BenefitCardService;
import com.example.AttendanceApp.services.EmployeesService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/benefit-card-employee/{employee}")
    public String benefitCardEmployeePage(Model model, @PathVariable("employee") Long employeeOrder){
        model.addAttribute("employee", employeesService.getEmployeeById(employeeOrder));
        return "benefit-card";
    }

    @GetMapping("/add-benefit-card/")
    public String getBenefitCardForm() {
        return "benefit-card";
    }

    @PostMapping("/add-benefit-card/")
    public String createBenefitCard(@ModelAttribute String serialNumber,
                                    @ModelAttribute int points,
                                    @ModelAttribute Employee employee) {
        BenefitCard newBenefitCard = new BenefitCard(serialNumber, points);
        newBenefitCard.setEmployee(employee);
        benefitCardService.createBenefitCard(newBenefitCard);
        return "redirect:/benefits";
    }
}
