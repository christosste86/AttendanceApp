package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Separate;

import java.util.List;

public interface AssignmentService {

    List<Assignment> getAssignments();

    void createAssignment(Assignment assignment);

    void updateAssignmentTitle(long id, String title);

    void updateAssignmentHours(long id, int hoursPerWeek);

    void deleteAssignment(long id);

}
