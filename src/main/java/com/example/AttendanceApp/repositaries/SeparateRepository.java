package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Separate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeparateRepository extends JpaRepository<Separate, Long> {
}
