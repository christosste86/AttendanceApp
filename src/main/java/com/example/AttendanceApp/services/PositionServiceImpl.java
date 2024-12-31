package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Role;
import com.example.AttendanceApp.repositaries.PositionRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        for(Role role: Role.values()){
            roles.add(role);
        }return roles;
    }

    @Override
    public Position getPositionById(long id) {
        return positionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("Position with id (%s) not found.", id))
        );
    }

    @Override
    public void createPosition(Position position) {
        positionRepository.save(position);
    }

    @Override
    public void updatePosition(long id, String title, Role role) {
        Optional<Position> position = positionRepository.findById(id);
        if (position.isPresent()) {
            Position p = position.get();
            p.setTitle(title);
            p.setRole(role);
            positionRepository.save(p);
        }
    }

    @Override
    public void deletePosition(long id) {
        Optional<Position> position = positionRepository.findById(id);
        if (position.isPresent()) {
            positionRepository.deleteById(id);
        }
    }

}
