package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Separate;

import java.util.List;

public interface AssignmentService {

    List<Assignment> getAssignments();

    Assignment getAssignmentById(long id);

    boolean isExist(String title, int hoursPerWeek);

    void createAssignment(Assignment assignment);

    void updateAssignment(long id, String title, int hoursPerWeek);

    void deleteAssignment(long id);

}
