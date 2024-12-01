package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Schedule;
import com.example.AttendanceApp.repositaries.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
}
