package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Position;

import java.util.List;

public interface PositionService {
    List<Position> getPositions();

    void createPosition(Position position);

    void updatePosition(long id);

    void deletePosition(long id);
}
