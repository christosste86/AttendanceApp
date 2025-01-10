package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    @Query("select a from assignments a where a.hoursPerWeek = :hoursPerWeek")
    Optional<Assignment> findByHoursPerWeek(@Param("hoursPerWeek") int hoursPerWeek);

    @Query("select a from assignments a where a.assignmentTitle = :assignmentTitle or a.hoursPerWeek = :hoursPerWeek")
    Optional<Assignment> findByAssignmentTitleAndHoursPerWeek(String assignmentTitle, int hoursPerWeek);
}
