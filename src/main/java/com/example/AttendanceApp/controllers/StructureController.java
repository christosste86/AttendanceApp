package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.services.AssignmentService;
import com.example.AttendanceApp.services.PositionService;
import com.example.AttendanceApp.services.SeparateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StructureController {
    private final AssignmentService assignmentService;
    private final PositionService positionService;
    private final SeparateService separateService;

    public StructureController(AssignmentService assignmentService, PositionService positionService, SeparateService separateService) {
        this.assignmentService = assignmentService;
        this.positionService = positionService;
        this.separateService = separateService;
    }

    @GetMapping("/add-assignment/")
    public String getAssignmentForm(Model model){
        model.addAttribute("assignmentsList", assignmentService.getAssignments());
        return "structure";
    }

    @PostMapping("/add-assignment/")
    public String createAssignment(@RequestParam String title,
                                   @RequestParam int assignment){
        Assignment newAssignment = new Assignment(title, assignment);
        return "redirect:/structure/";
    }

    @GetMapping("/add-Possition/")
    public String getPossitionForm(Model model){
        model.addAttribute("possitionList", positionService.getPositions());
        return "structure";
    }

    @PostMapping("/add-Possition/")
    public String createPossition(@RequestParam String title){
        Position newPossition = new Position(title);
        return "redirect:/structure/";
    }

    @GetMapping("/add-separate/")
    public String getSeparateForm(Model model){
        model.addAttribute("separateList", separateService.getSeparates());
        return "structure";
    }

    @GetMapping("/add-separate/")
    public String createSeparateForm(@RequestParam String title,
                                     @RequestParam String description){
        Separate newSeparate = new Separate(title, description);
        return "redirect:/structure/";
    }

}
