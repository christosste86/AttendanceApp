package com.example.AttendanceApp.models;

import jakarta.persistence.Entity;

import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class FavoriteShift extends BaseEntity{
    private String shiftName;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;

    public FavoriteShift() {
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getFormatedTime(){
        DecimalFormat df = new DecimalFormat("00");
        return String.format("%s:%s %s:%s",df.format(this.shiftStart.getHour())
                , df.format(this.shiftStart.getMinute())
                , df.format(this.shiftEnd.getHour())
                , df.format(this.shiftEnd.getMinute()));
    }
}
