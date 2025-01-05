package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.BenefitCard;
import com.example.AttendanceApp.models.Separate;

import java.util.List;

public interface BenefitCardService {
    List<BenefitCard> getBenefitCards();

    BenefitCard getBenefitCardById(long id);

    BenefitCard getBenefitCardBySerialNumber(String serialNumber);

    void createBenefitCard(BenefitCard benefitCard);

    void updateBenefitCardPoints(long id, int points);

    void updateBenefitCardSerialNumber(long id, String SerialNumber);

    void deleteBenefitCard(long id);
}
