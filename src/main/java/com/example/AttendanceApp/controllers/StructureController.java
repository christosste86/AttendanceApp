package com.example.AttendanceApp.controllers;

import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Role;
import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.services.AssignmentService;
import com.example.AttendanceApp.services.PositionService;
import com.example.AttendanceApp.services.SeparateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StructureController {
    private final AssignmentService assignmentService;
    private final PositionService positionService;
    private final SeparateService separateService;
    private boolean isAssignmentUpdate = false;
    private boolean isPositionUpdate = false;
    private boolean isSeparateUpdate = false;
    private long assignmentId;
    private Assignment assignment;
    private long positionId;
    private Position position;
    private long separateId;
    private Separate separate;

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
        model.addAttribute("isAssignmentUpdate", isAssignmentUpdate);
        model.addAttribute("isPositionUpdate", isPositionUpdate);
        model.addAttribute("isSeparateUpdate", isSeparateUpdate);
        model.addAttribute("assignment", this.assignment);
        model.addAttribute("position", this.position);
        model.addAttribute("separate", this.separate);
        model.addAttribute("roles", positionService.getRoles());
        return "structure";
    }

    @GetMapping("/add-update-assignment")
    public String getAssignmentForm(Model model) {
        model.addAttribute("separateList", separateService.getSeparates());
        return "structure";
    }

    @PostMapping("/add-update-assignment")
    public String createUpdateAssignment(@RequestParam String assignmentTitle,
                                   @RequestParam int assignment){
        if(isAssignmentUpdate){
            assignmentService.updateAssignment(this.assignmentId, assignmentTitle, assignment);
            this.isAssignmentUpdate = false;
        }else{
            Assignment newAssignment = new Assignment(assignmentTitle, assignment);
            assignmentService.createAssignment(newAssignment);
        }
        return "redirect:/structure";
    }

    @GetMapping("/delete-assignment/{id}")
    public String deleteAssignment(@PathVariable("id") Long assignmentOrder){
        assignmentService.deleteAssignment(assignmentOrder);
        return "redirect:/structure";
    }

    @GetMapping("/get-assignment-id/{id}")
    public String getAssignmentId(@PathVariable("id") Long assignmentOrder)
    {
        this.assignmentId = assignmentOrder;
        this.isAssignmentUpdate = true;
        this.assignment = assignmentService.getAssignmentById(this.assignmentId);
        System.out.println("Assignment Id: "+assignmentOrder);
        return "redirect:/structure";
    }

    @GetMapping("/is-assignment-update-false")
    public String isUpdateAssignmentFalse(){
        this.isAssignmentUpdate = false;
        return "redirect:/structure";
    }

    @GetMapping("/add-position/")
    public String getPositionForm(Model model){
        model.addAttribute("positionList", positionService.getPositions());
        return "structure";
    }

    @PostMapping("/add-update-position")
    public String createUpdatePosition(@RequestParam String title,
                                       @RequestParam String sortTitle,
                                       @RequestParam String position_role){
        if(isPositionUpdate){
            positionService.updatePosition(this.positionId, title, sortTitle, position_role);;
            this.isPositionUpdate = false;
        }else{
            Position newPosition = new Position(title, sortTitle, position_role);
            positionService.createPosition(newPosition);
        }
        return "redirect:/structure";
    }

    @GetMapping("/delete-position/{id}")
    public String deletePosition(@PathVariable("id") Long positionOrder){
        positionService.deletePosition(positionOrder);
        return "redirect:/structure";
    }

    @GetMapping("/get-position-id/{id}")
    public String getPositionId(@PathVariable("id") Long positionOrder)
    {
        this.isPositionUpdate = true;
        this.positionId = positionOrder;
        this.position = positionService.getPositionById(this.positionId);
        return "redirect:/structure";
    }

    @GetMapping("/is-position-update-false")
    public String isUpdatePositionFalse(){
        this.isPositionUpdate = false;
        return "redirect:/structure";
    }

    @GetMapping("/add-update-separate")
    public String getSeparateForm(){
        return "structure";
    }

    @PostMapping("/add-update-separate")
    public String createSeparate(@RequestParam String title,
                                 @RequestParam String description){
        if(isSeparateUpdate){
            separateService.updateSeparateTitle(this.separateId, title, description);
            this.isSeparateUpdate = false;
        }else{
            Separate newSeparate = new Separate(title, description);
            separateService.createSeparate(newSeparate);
        }
        return "redirect:/structure";
    }

    @GetMapping("/delete-separate/{id}")
    public String deleteSeparate(@PathVariable("id") Long separateOrder){
        separateService.deleteSeparate(separateOrder);
        return "redirect:/structure";
    }

    @GetMapping("/get-separate-id/{id}")
    public String getSeparateId(@PathVariable("id") Long separateOrder)
    {
        this.isSeparateUpdate = true;
        this.separateId = separateOrder;
        this.separate = separateService.getSeparateById(this.separateId);
        return "redirect:/structure";
    }

    @GetMapping("/is-separate-update-false")
        public String isUpdateSeparateFalse(){
            this.isSeparateUpdate = false;
            return "redirect:/structure";
        }

}
