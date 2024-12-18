package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.repositaries.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public List<Assignment> getAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public void createAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

    @Override
    public void updateAssignment(long id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        if (assignment.isPresent()) {
            Assignment a = assignment.get();
        }
    }

    @Override
    public void deleteAssignment(long id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        if(assignment.isPresent()) {
            assignmentRepository.delete(assignment.get());
        }

    }
}
