package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedules s where s.employee = :employee and s.shiftStart >= :shiftStar and s.shiftEnd <= :shiftEnd")
    List<Schedule> findByEmployeeWorkingShiftDateAndTime(@Param("employee") Employee employee,
                                                         @Param("shiftStar") LocalDateTime shiftStar,
                                                         @Param("shiftEnd") LocalDateTime shiftEnd);


    @Query("select s from Schedules s where s.employee = :employee and s.shiftStart between :startLocalDay and :endLocalDay")
    List<Schedule> findScheduleByEmployeeAndSelectedMonth(@Param("employee") Employee employee,
                                                          @Param("startLocalDay") LocalDateTime startLocalDay,
                                                          @Param("endLocalDay") LocalDateTime endLocalDay);

    @Query("select s from Schedules s where s.employee = :employee and year(s.shiftStart) = :yearValue and month(s.shiftStart) = :monthValue and day(s.shiftStart) = :dayValue")
    Optional<Schedule> findScheduleByEmployeeAndSelectedDay(@Param("employee") Employee employee,
                                                          @Param("yearValue") int yearValue,
                                                          @Param("monthValue") int monthValue,
                                                        @Param("dayValue") int dayValue);

}
