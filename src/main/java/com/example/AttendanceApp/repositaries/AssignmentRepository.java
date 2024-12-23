package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    @Query("select a from assignments a where a.id = :id")
    List<Assignment> findByAssignmentId(@Param("id") Long id);
}
