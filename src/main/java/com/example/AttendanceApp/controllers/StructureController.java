package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.services.AssignmentService;
import com.example.AttendanceApp.services.PositionService;
import com.example.AttendanceApp.services.SeparateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StructureController {
    private final AssignmentService assignmentService;
    private final PositionService positionService;
    private final SeparateService separateService;

    @Autowired
    public StructureController(AssignmentService assignmentService, PositionService positionService, SeparateService separateService) {
        this.assignmentService = assignmentService;
        this.positionService = positionService;
        this.separateService = separateService;
    }

    @GetMapping("structure")
    public String structure(Model model) {
        model.addAttribute("assignmentList", assignmentService.getAssignments());
        model.addAttribute("positionList", positionService.getPositions());
        model.addAttribute("separateList", separateService.getSeparates());
        return "structure";
    }

    @GetMapping("/add-assignment/")
    public String getAssignmentForm(){
        return "structure";
    }

    @PostMapping("/add-assignment/")
    public String createAssignment(@RequestParam String title,
                                   @RequestParam int assignment){
        Assignment newAssignment = new Assignment(title, assignment);
        assignmentService.createAssignment(newAssignment);
        return "redirect:/structure/";
    }

    @GetMapping("/add-position/")
    public String getPositionForm(){
        return "structure";
    }

    @PostMapping("/add-position/")
    public String createPosition(@RequestParam String title){
        Position newPosition = new Position(title);
        positionService.createPosition(newPosition);
        return "redirect:/structure/";
    }

    @GetMapping("/add-separate/")
    public String getSeparateForm(){
        return "structure";
    }

    @PostMapping("/add-separate/")
    public String createSeparate(@RequestParam String title,
                                     @RequestParam String description){
        Separate newSeparate = new Separate(title, description);
        separateService.createSeparate(newSeparate);
        return "redirect:/structure/";
    }

}
