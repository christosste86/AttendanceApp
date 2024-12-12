package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s where month(s.shiftStart) = :month and year(s.shiftStart) = :year")
    List<Schedule> findByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
