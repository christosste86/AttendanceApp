package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.repositaries.SeparateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeparateServicelmpl implements SeparateService {

    private final SeparateRepository separateRepository;

    public SeparateServicelmpl(SeparateRepository separateRepository) {
        this.separateRepository = separateRepository;
    }

    @Override
    public List<Separate> getSeparates() {
        return separateRepository.findAll();
    }
}
