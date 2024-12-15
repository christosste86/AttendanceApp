package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s where month(s.shiftStart) = :month and year(s.shiftStart) = :year")
    List<Schedule> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("select s from Schedule s where s.employee = :employee and s.shiftStart >= :shiftStar and s.shiftEnd <= :shiftEnd")
    List<Schedule> findByEmployeeWorkingShiftDateAndTime(@Param("employee") Employee employee,
                                                         @Param("shiftStar") LocalDateTime shiftStar,
                                                         @Param("shiftEnd") LocalDateTime shiftEnd);

    @Query("select s from Schedule s where s.employee = :employee and year(s.shiftStart) = :yearValue and month(s.shiftStart) = :monthValue")
    List<Schedule> findScheduleByEmployeeAndSelectedMonth(@Param("employee") Employee employee,
                                                          @Param("year") int yearValue,
                                                          @Param("month") int monthValue);
}
