package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.Separate;

import java.util.List;

public interface SeparateService {

    List<Separate> getSeparates();

    void createSeparate(Separate separate);

    void updateSeparateTitle(long id, String title);

    void deleteSeparate(long id);

}
