package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.repositaries.SeparateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void createSeparate(Separate separate) {
        separateRepository.save(separate);
    }

    @Override
    public void updateSeparateTitle(long id, String title) {
        Optional<Separate> separate = separateRepository.findById(id);
        if (separate.isPresent()) {
            Separate s = separate.get();
            s.setTitle(title);
        }
    }

    @Override
    public void deleteSeparate(long id) {
        Optional<Separate> separate = separateRepository.findById(id);
        if (separate.isPresent()) {
            separateRepository.delete(separate.get());
        }
    }
}
