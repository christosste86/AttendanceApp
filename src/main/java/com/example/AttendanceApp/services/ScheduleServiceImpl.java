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


    private void addEmptySchedule(List<Schedule> schedule, LocalDate startLocalDate, LocalDate endLocalDate){
        if(schedule.isEmpty()){
            for (LocalDate day = startLocalDate; !day.isAfter(endLocalDate); day = day.plusDays(1)) {
                schedule.add(new Schedule());
            }
        }
        System.out.println("EmptySchedule: " + schedule.size());
    }

    @Override
    public LinkedHashMap<Employee, List<Schedule>> employeesScheduleHashMapPerMonth(List<Employee> employees, LocalDate startLocalDate, LocalDate endLocalDate){
        LinkedHashMap<Employee, List<Schedule>> employeesScheduleHashMapPerMonth = new LinkedHashMap<>();
        for (Employee e: employees){
            List<Schedule> schedule = new ArrayList<>();
            addEmptySchedule(schedule, startLocalDate, endLocalDate);
            int index = 0;
            for (LocalDate day = startLocalDate; !day.isAfter(endLocalDate); day = day.plusDays(1)) {
                List<Schedule> employeeScheduleSelectedMonth = scheduleRepository.findScheduleByEmployeeAndSelectedMonth(e, startLocalDate.atStartOfDay(), endLocalDate.atTime(23,59,59));
                if(!employeeScheduleSelectedMonth.isEmpty()) {
                    for (Schedule empMonthSchedule : employeeScheduleSelectedMonth) {
                        if (empMonthSchedule.getShiftStart().toLocalDate().isEqual(day)) {
                            schedule.remove(index);
                            schedule.add(index, empMonthSchedule);
                        }
                    }
                }
                index = index + 1;
            }
            employeesScheduleHashMapPerMonth.put(e, schedule);
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
    public HashMap<Employee, Details> monthlyTotalHours(LocalDate month, HashMap<Employee, List<Schedule>> employeesScheduleHashMapPerMonth){
        HashMap<Employee, Details> totalHours = new HashMap<>();
        for (Employee e: employeesScheduleHashMapPerMonth.keySet()){
            double totalHour = 0.0;
            Integer shifts = 0;
            for (Schedule s: employeesScheduleHashMapPerMonth.get(e)){
                if(s.getShiftStart() != null && s.getShiftEnd() != null){
                    totalHour += Duration.between(s.getShiftStart(), s.getShiftEnd()).toMinutes()/60.0;
                    shifts ++;
                }
            }
            totalHour = new BigDecimal(totalHour).setScale(2, RoundingMode.HALF_UP).doubleValue();
            totalHours.put(e, new Details(totalHour, shifts, monthlyFullTimeHours(month, e.getAssignment().getHoursPerWeek())));
        }return totalHours;
    }

    @Override
    public Double monthlyFullTimeHours(LocalDate month, int assignment) {
        int weekDays = 0;
        for (int day = 1; day <= month.withDayOfMonth(1).lengthOfMonth(); day++) {
            if (month.withDayOfMonth(day).getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
            month.withDayOfMonth(day).getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                weekDays = weekDays + 1;
            }
        }
        return (month.withDayOfMonth(1).lengthOfMonth() - weekDays) * (assignment / 5.0);
    }

}
