package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Role;

import java.util.List;

public interface PositionService {
    List<Position> getPositions();

    List<Role> getRoles();

    Position getPositionById(long id);

    Role getRoleByName(String roleName);

    void createPosition(Position position);

    void updatePosition(long id, String title,String sortTitle, String role);

    void deletePosition(long id);
}
