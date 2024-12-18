package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.BenefitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitCardRepository extends JpaRepository<BenefitCard, Long> {

}
