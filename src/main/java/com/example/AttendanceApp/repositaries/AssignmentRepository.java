package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
