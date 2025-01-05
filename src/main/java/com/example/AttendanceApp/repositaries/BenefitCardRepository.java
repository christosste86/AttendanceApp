package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.BenefitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BenefitCardRepository extends JpaRepository<BenefitCard, Long> {
    @Query("select b from benefit_cards b where b.serialNumber = :employeeBenefitCardSerialNumber")
    Optional<BenefitCard> findBySerialNumber(String employeeBenefitCardSerialNumber);
}
