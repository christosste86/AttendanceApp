package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.repositaries.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void createPosition(Position position) {
        positionRepository.save(position);
    }

    @Override
    public void updatePosition(long id) {
        Optional<Position> position = positionRepository.findById(id);
        if (position.isPresent()) {
            Position p = position.get();
        }
    }

    @Override
    public void deletePosition(long id) {
        Optional<Position> position = positionRepository.findById(id);
        if (position.isPresent()) {
            positionRepository.delete(position.get());
        }
    }

}
