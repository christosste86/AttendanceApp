package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.BenefitCard;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.repositaries.BenefitCardRepository;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BenefitCardServiceImpl implements BenefitCardService {
    private final BenefitCardRepository benefitCardRepository;

    public BenefitCardServiceImpl(BenefitCardRepository benefitCardRepository) {
        this.benefitCardRepository = benefitCardRepository;
    }

    @Override
    public List<BenefitCard> getBenefitCards() {
        return benefitCardRepository.findAll();
    }

    @Override
    public BenefitCard getBenefitCardById(long id) {
        Optional<BenefitCard> benefitCard = benefitCardRepository.findById(id);
        return benefitCard.orElse(null);
    }

    @Override
    public void createBenefitCard(BenefitCard benefitCard) {
        benefitCardRepository.save(benefitCard);
    }

    @Override
    public void updateBenefitCardPoints(long id, int points) {
        Optional<BenefitCard> benefitCard = benefitCardRepository.findById(id);
        if (benefitCard.isPresent()){
            BenefitCard b = benefitCard.get();
            b.setPoints(b.getPoints() + points);
        }
    }

    @Override
    public void updateBenefitCardSerialNumber(long id, String SerialNumber) {
        Optional<BenefitCard> benefitCard = benefitCardRepository.findById(id);
        if (benefitCard.isPresent()){
            BenefitCard b = benefitCard.get();
            b.setSerialNumber(SerialNumber);
        }
    }

    @Override
    public void deleteBenefitCard(long id) {
        Optional<BenefitCard> benefitCard = benefitCardRepository.findById(id);
        if (benefitCard.isPresent()){
            benefitCardRepository.delete(benefitCard.get());
        }
    }
}
