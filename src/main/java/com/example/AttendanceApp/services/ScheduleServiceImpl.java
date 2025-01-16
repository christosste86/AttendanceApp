package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Details;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Schedule;
import com.example.AttendanceApp.repositaries.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<Schedule> getSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule getScheduleByEmployeeDate(Employee employee, LocalDate shiftDay) {
        Optional<Schedule> schedule = scheduleRepository.findScheduleByEmployeeAndSelectedDay(
                employee,
                shiftDay.getYear(),
                shiftDay.getMonthValue(),
                shiftDay.getDayOfMonth()
        );
        if(schedule.isPresent()) {
            return schedule.get();
        }
        return null;
    }

    @Override
    public boolean scheduleExists(Employee employee, LocalDateTime shiftStart, LocalDateTime shiftEnd) {
        List<Schedule> schedules = scheduleRepository.findByEmployeeWorkingShiftDateAndTime(employee, shiftStart, shiftEnd);
        return !schedules.isEmpty();
    }

    @Override
    public void saveSchedule(Schedule schedule) {
        double workedHours = Duration.between(schedule.getShiftStart(), schedule.getShiftEnd()).toMinutes()/60.0;
        schedule.setWorkedHours(workedHours);
        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @Override
    public void updateScheduleById(long id, LocalDateTime shiftStart, LocalDateTime shiftEnd) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isPresent()){
            Schedule s = schedule.get();
            s.setShiftStart(shiftStart);
            s.setShiftEnd(shiftEnd);
            double workedHours = Duration.between(shiftStart, shiftEnd).toMinutes()/60.0;
            s.setWorkedHours(workedHours);
        }
    }


    private void addEmptySchedule(LinkedHashMap<LocalDate, Schedule> schedule, LocalDate startLocalDate, LocalDate endLocalDate){
        if(schedule.isEmpty()){
            for (LocalDate day = startLocalDate; !day.isAfter(endLocalDate); day = day.plusDays(1)) {
                schedule.put(day, new Schedule());
            }
        }
        System.out.println("EmptySchedule: " + schedule.size());
    }

    @Override
    public LinkedHashMap<Employee, LinkedHashMap<LocalDate, Schedule>> getEmployeesScheduleForPeriod(List<Employee> employees, LocalDate startLocalDate, LocalDate endLocalDate){
        LinkedHashMap<Employee, LinkedHashMap<LocalDate, Schedule>> employeesScheduleHashMapPerMonth = new LinkedHashMap<>();
        for (Employee e: employees){
            LinkedHashMap<LocalDate, Schedule> scheduleByDate = new LinkedHashMap<>();
            for (LocalDate day = startLocalDate; !day.isAfter(endLocalDate); day = day.plusDays(1)) {
                Optional<Schedule> schedule = scheduleRepository.findScheduleByEmployeeAndSelectedDay(
                        e,
                        day.getYear(),
                        day.getMonthValue(),
                        day.getDayOfMonth());
                if(schedule.isPresent()) {

                    scheduleByDate.put(day, schedule.get());
                }else{
                    scheduleByDate.put(day, new Schedule());
                }
            }
            employeesScheduleHashMapPerMonth.put(e, scheduleByDate);
        }return employeesScheduleHashMapPerMonth;
    }

    @Override
    public boolean isEmployeeDayExist(Employee employee, LocalDate day){
        Optional<Schedule> schedule = scheduleRepository.findScheduleByEmployeeAndSelectedDay(
                employee,
                day.getYear(),
                day.getMonthValue(),
                day.getDayOfMonth());
        if(schedule.isPresent()) {
            return true;
        }return false;
    }

    @Override
    public LinkedHashMap<Employee, Details> getPeriodDetailsPerEmployee(List <Employee> employees, LocalDate startPeriod, LocalDate endPeriod){
        LinkedHashMap<Employee, Details> employeesDetails = new LinkedHashMap<>();
        for(Employee e: employees){
            Details employeePeriodDetails = new Details();
            List<Schedule> employeePeriodSchedule = scheduleRepository.findScheduleByEmployeeAndPeriod(
                    e,
                    startPeriod.atStartOfDay(),
                    endPeriod.atTime(23,59,59)
            );
            employeePeriodDetails.setTotalHours(totalPeriodHours(employeePeriodSchedule));
            employeePeriodDetails.setShifts(totalPeriodShifts(employeePeriodSchedule));
            employeePeriodDetails.setWorkingHours(periodWorkingHours(e, startPeriod, endPeriod));
            employeesDetails.put(e, employeePeriodDetails);
        }
        return employeesDetails;
    }

    private Double totalPeriodHours(List<Schedule> employeePeriodSchedule){
        return employeePeriodSchedule
                .stream()
                .mapToDouble(s-> Duration.between(s.getShiftStart(), s.getShiftEnd()).toMinutes()).sum() / 60;
    }

    private Integer totalPeriodShifts( List<Schedule> employeePeriodSchedule){
        return employeePeriodSchedule.size();
    }

    private int periodWorkingHours(Employee employee, LocalDate startPeriod, LocalDate endPeriod){
        int periodWorkingDays = 0;
        int weekendDays = 0;
        for(LocalDate day = startPeriod; !day.isAfter(endPeriod); day = day.plusDays(1)) {
            periodWorkingDays = periodWorkingDays + 1;
            if(!day.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
            !day.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                periodWorkingDays = periodWorkingDays + 1;
            }
        }return (periodWorkingDays - weekendDays) * (employee.getAssignment().getHoursPerWeek() / 5);
    }

}
