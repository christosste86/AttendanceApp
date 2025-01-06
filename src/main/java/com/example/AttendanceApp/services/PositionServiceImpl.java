package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Role;
import com.example.AttendanceApp.repositaries.PositionRepository;
import com.example.AttendanceApp.repositaries.RoleRepository;
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
    private final RoleRepository roleRepository;

    public PositionServiceImpl(PositionRepository positionRepository, RoleRepository roleRepository) {
        this.positionRepository = positionRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return List.of(
                new Role("ROLE_ADMIN"),
                new Role("ROLE_LEVEL1"),
                new Role("ROLE_LEVEL2"),
                new Role("ROLE_LEVEL3"));
    }

    @Override
    public Position getPositionById(long id) {
        return positionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("Position with id (%s) not found.", id))
        );
    }

    @Override
    public Role getRoleByName(String roleName) {
        if(roleRepository.findByPositionRoleName(roleName).isEmpty()) {
            return new Role();
        }return roleRepository.findByPositionRoleName(roleName).getFirst();
    }

    @Override
    public void createPosition(Position position) {
        positionRepository.save(position);
    }

    @Override
    public void updatePosition(long id, String title, String sortTitle, String role) {
        Optional<Position> position = positionRepository.findById(id);
        if (position.isPresent()) {
            Position p = position.get();
            p.setTitle(title);
            p.setSortTitle(sortTitle);
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
