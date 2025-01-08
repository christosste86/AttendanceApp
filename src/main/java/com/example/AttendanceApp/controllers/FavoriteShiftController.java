package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.repositaries.FavoriteShiftRepository;
import com.example.AttendanceApp.services.FavoriteShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FavoriteShiftController {

    private final FavoriteShiftService favoriteShiftService;

    @Autowired
    public FavoriteShiftController(FavoriteShiftService favoriteShiftService) {
        this.favoriteShiftService = favoriteShiftService;
    }

}
