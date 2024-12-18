package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.BenefitCard;
import com.example.AttendanceApp.services.BenefitCardService;
import org.springframework.ui.Model;
import com.example.AttendanceApp.repositaries.BenefitCardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
