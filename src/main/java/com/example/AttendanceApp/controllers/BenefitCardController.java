package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.BenefitCard;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.services.BenefitCardService;
import org.springframework.ui.Model;
import com.example.AttendanceApp.repositaries.BenefitCardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BenefitCardController {
    private final BenefitCardService benefitCardService;

    public BenefitCardController(BenefitCardService benefitCardService) {
        this.benefitCardService = benefitCardService;
    }

    @GetMapping("/benefits")
    public String benefitsPage(Model model) {
        model.addAttribute("benefitCardsList", benefitCardService.getBenefitCards());
        return "benefits";
    }

    @GetMapping("/add-benefit-card/")
    public String getBenefitCardForm() {
        return "benefits";
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
