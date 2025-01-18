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
    @Query("select s from Schedules s where s.employee = :employee and s.shiftEnd between :startLocalDay and :endLocalDay")
    List<Schedule> findScheduleByEmployeeAndPeriod(@Param("employee") Employee employee,
                                                          @Param("startLocalDay") LocalDateTime startLocalDay,
                                                          @Param("endLocalDay") LocalDateTime endLocalDay);

    @Query("select s from Schedules s where s.employee = :employee and year(s.shiftStart) = :yearValue and month(s.shiftStart) = :monthValue and day(s.shiftStart) = :dayValue")
    Optional<Schedule> findScheduleByEmployeeAndSelectedDay(@Param("employee") Employee employee,
                                                          @Param("yearValue") int yearValue,
                                                          @Param("monthValue") int monthValue,
                                                        @Param("dayValue") int dayValue);

    @Query("select s from Schedules s where s.employee = :employee and s.shiftStart between :startDay and :endDay")
    List<Schedule> findScheduleByEmployeeBetweenDays(@Param("employee") Employee employee,
                                                     @Param ("startDay") LocalDateTime startDay,
                                                     @Param ("endDay") LocalDateTime endDay);

    @Query(value = "SELECT SUM(TIMESTAMPDIFF(MINUTE, s.shiftStart, s.shiftEnd)) " +
            "FROM Schedules s " +
            "WHERE s.employee_id = :employeeId " +
            "AND s.shiftStart >= :startLocalDay " +
            "AND s.shiftEnd <= :endLocalDay",
            nativeQuery = true)
    Double getEmployeePeriodTotalHours(@Param("employeeId") Long employeeId,
                                       @Param("startLocalDay") LocalDateTime startLocalDay,
                                       @Param("endLocalDay") LocalDateTime endLocalDay);


}
