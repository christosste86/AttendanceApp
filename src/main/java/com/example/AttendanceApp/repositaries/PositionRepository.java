package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PositionRepository extends JpaRepository<Position, Long> {

}
