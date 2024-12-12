package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Details;
import com.example.AttendanceApp.models.Employees;
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
    public Schedule getScheduleById(long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("Location with id (%s) not found.", id))
        );
    }

    @Override
    public boolean scheduleExists(long employeeId, LocalDateTime shiftStart, LocalDateTime shiftEnd, Double workedHours, boolean isPresent) {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().filter(e ->
                        Objects.equals(e.getEmployeeId(), employeeId) &&
                        Objects.equals(e.getShiftStart(), shiftStart) &&
                        Objects.equals(e.getShiftEnd(), shiftEnd) &&
                        Objects.equals(e.getWorkedHours(), workedHours) &&
                        Objects.equals(e.isPresent(), isPresent)).toList().isEmpty();
    }

    @Override
    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
        System.out.println("Schedule saved: " + schedule);
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    @Override
    public void updateScheduleById(long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isPresent()){
            Schedule s = schedule.get();
        }
    }

    @Override
    public List<Schedule>monthlyEmployeesSchedule(LocalDate month){
        return scheduleRepository.findAll();
    }

    private List<Schedule> monthlyEmployeesSchedule(LocalDate month, Employees employee){
        return scheduleRepository.findByMonthAndYear(month.getMonthValue(), month.getYear())
                .stream()
                .filter(s-> s.getEmployeeId() == employee.getId()).toList();
    }

    private void addEmptySchedule(List<Schedule> schedule, LocalDate month){
        if(schedule.isEmpty()){
            for (int i = 1; i <= month.lengthOfMonth(); i++) {
                schedule.add(new Schedule());
            }
        }
    }

    @Override
    public HashMap<Employees, List<Schedule>> employeesScheduleHashMapPerMonth(LocalDate month, List<Employees> employees){
        HashMap<Employees, List<Schedule>> employeesScheduleHashMapPerMonth = new HashMap<>();
        List<Schedule> monthlySchedule = scheduleRepository.findByMonthAndYear(month.getMonthValue(), month.getYear());

        for (Employees e: employees){
            List<Schedule> schedule = new ArrayList<>();
            for (int i = 1; i <= month.lengthOfMonth(); i++) {
                for (Schedule s: monthlySchedule){
                    addEmptySchedule(schedule, month);
                    if(s.getEmployeeId() == e.getId()){
                        if(s.getShiftStart().getDayOfMonth() == i) {
                            schedule.remove(s.getShiftStart().getDayOfMonth()-1);
                            schedule.add(s.getShiftStart().getDayOfMonth()-1, s);
                        }
                    }
                }
            }
            addEmptySchedule(schedule, month);
            employeesScheduleHashMapPerMonth.put(e, schedule);
        }return employeesScheduleHashMapPerMonth;
    }

    @Override
    public HashMap<Employees, Details> monthlyTotalHours(LocalDate month, HashMap<Employees, List<Schedule>> employeesScheduleHashMapPerMonth){
        HashMap<Employees, Details> totalHours = new HashMap<>();
        for (Employees e: employeesScheduleHashMapPerMonth.keySet()){
            double totalHour = 0.0;
            Integer shifts = 0;
            for (Schedule s: employeesScheduleHashMapPerMonth.get(e)){
                if(s.getShiftStart() != null && s.getShiftEnd() != null){
                    totalHour += Duration.between(s.getShiftStart(), s.getShiftEnd()).toMinutes()/60.0;
                    shifts ++;
                }
            }
            totalHour = new BigDecimal(totalHour).setScale(2, RoundingMode.HALF_UP).doubleValue();
            totalHours.put(e, new Details(totalHour, shifts, monthlyFullTimeHours(month, 40)));
        }return totalHours;
    }

    @Override
    public Double monthlyFullTimeHours(LocalDate month, int assignment){
        double totalHour = 0.0;
        for (int day = 1; day <= month.withDayOfMonth(1).lengthOfMonth(); day++) {
            if(month.withDayOfMonth(day).getDayOfWeek() != DayOfWeek.SATURDAY
                    && month.withDayOfMonth(day).getDayOfWeek() != DayOfWeek.SUNDAY){
                totalHour += assignment / 8.0;
            }
        }return totalHour;
    }
}
