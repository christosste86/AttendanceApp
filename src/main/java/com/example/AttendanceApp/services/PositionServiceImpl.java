package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.repositaries.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

}
