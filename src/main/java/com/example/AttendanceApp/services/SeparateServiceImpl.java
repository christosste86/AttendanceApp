package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.repositaries.SeparateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeparateServiceImpl implements SeparateService {

    private final SeparateRepository separateRepository;

    public SeparateServiceImpl(SeparateRepository separateRepository) {
        this.separateRepository = separateRepository;
    }

    @Override
    public List<Separate> getSeparates() {
        return separateRepository.findAll();
    }

    @Override
    public Separate getSeparateById(long id) {
        return separateRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("Separate with id (%s) not found.", id))
        );
    }

    @Override
    public boolean isExistBySeparateTitle(String separateTitle) {
        Optional<Separate> separate = separateRepository.findByTitle(separateTitle);
        return separate.isPresent();
    }

    @Override
    public void createSeparate(Separate separate) {
        separateRepository.save(separate);
    }

    @Override
    public void updateSeparateTitle(long id, String title, String description) {
        Optional<Separate> separate = separateRepository.findById(id);
        if (separate.isPresent()) {
            Separate s = separate.get();
            s.setTitle(title);
            s.setDescription(description);
            separateRepository.save(s);
        }
    }

    @Override
    public void deleteSeparate(long id) {
        Optional<Separate> separate = separateRepository.findById(id);
        if (separate.isPresent()) {
            separateRepository.deleteById(id);
        }
    }
}
