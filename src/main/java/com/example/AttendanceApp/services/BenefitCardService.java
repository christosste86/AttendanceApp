package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.BenefitCard;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Separate;

import java.util.List;

public interface BenefitCardService {
    List<BenefitCard> getBenefitCards();

    BenefitCard getBenefitCardById(long id);

    void createBenefitCard(BenefitCard benefitCard);

    boolean isExist(String serialNumber);

    void updateBenefitCardPoints(BenefitCard benefitCard);

    void updateBenefitCard(long id,String SerialNumber, int credit, Employee employee);

    void deleteBenefitCard(long id);
}
