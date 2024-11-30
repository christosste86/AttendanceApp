package com.example.AttendanceApp.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ScheduleController {
    //scheduleService

    @GetMapping("/")
    public String getMainPage(Model model){
        //get Schedule
        return "index";
    }
}
