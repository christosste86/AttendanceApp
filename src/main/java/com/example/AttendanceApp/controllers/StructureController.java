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

    //Services
    private final AssignmentService assignmentService;
    private final PositionService positionService;
    private final SeparateService separateService;

    //Change to Update or Add
    private boolean isAssignmentUpdate = false;
    private boolean isPositionUpdate = false;
    private boolean isSeparateUpdate = false;

    //Save Actual
    private Assignment assignment;
    private Position position;
    private Separate separate;

    //Css Style
    private String assignmentCardClassCss = "hide";
    private String positionCardClassCss = "hide";
    private String separateCardClassCss = "hide";

    @Autowired
    public StructureController(AssignmentService assignmentService, PositionService positionService, SeparateService separateService) {
        this.assignmentService = assignmentService;
        this.positionService = positionService;
        this.separateService = separateService;
    }

    @GetMapping("structure")
    public String structure(Model model) {
        //Lists from databases
        model.addAttribute("assignmentList", assignmentService.getAssignments());
        model.addAttribute("positionList", positionService.getPositions());
        model.addAttribute("separateList", separateService.getSeparates());
        //verify for add or update
        model.addAttribute("isAssignmentUpdate", isAssignmentUpdate);
        model.addAttribute("isPositionUpdate", isPositionUpdate);
        model.addAttribute("isSeparateUpdate", isSeparateUpdate);
        //actual
        model.addAttribute("assignment", this.assignment);
        model.addAttribute("position", this.position);
        model.addAttribute("separate", this.separate);
        model.addAttribute("roles", positionService.getRoles());
        //css style
        model.addAttribute("assignmentCardClassCss", this.assignmentCardClassCss);
        model.addAttribute("positionCardClassCss", this.positionCardClassCss);
        model.addAttribute("separateCardClassCss", this.separateCardClassCss);
        return "structure";
    }

    //Assignment

    //Add new position is this.isAssignmentUpdate = false or update if this.isAssignmentUpdate = true

    @PostMapping("/add-update-assignment")
    public String createUpdateAssignment(@RequestParam String assignmentTitle,
                                   @RequestParam int assignment){
        if(isAssignmentUpdate){
            assignmentService.updateAssignment(this.assignment.getId(), assignmentTitle, assignment);
            this.isAssignmentUpdate = false;
            this.positionCardClassCss="hide";
            this.separateCardClassCss="hide";
            this.assignmentCardClassCss="hide";
        }else{
            Assignment newAssignment = new Assignment(assignmentTitle, assignment);
            if(!assignmentService.isExist(assignmentTitle, assignment)){
                assignmentService.createAssignment(newAssignment);
            }
            this.positionCardClassCss="hide";
            this.separateCardClassCss="hide";
            this.assignmentCardClassCss="hide";
        }
        return "redirect:/structure";
    }

    @GetMapping("/delete-assignment/{id}")
    public String deleteAssignment(@PathVariable("id") Long assignmentOrder){
        assignmentService.deleteAssignment(assignmentOrder);
        return "redirect:/structure";
    }

    @GetMapping("/get-assignment-id/{id}")
    public String getAssignmentId(@PathVariable("id") Long assignmentId)
    {
        this.isAssignmentUpdate = true;
        this.assignment = assignmentService.getAssignmentById(assignmentId);
        this.separateCardClassCss="hide";
        this.positionCardClassCss="hide";
        this.assignmentCardClassCss="structureCard";
        return "redirect:/structure";
    }

    @GetMapping("/is-assignment-update-false")
    public String isUpdateAssignmentFalse(){
        this.isAssignmentUpdate = false;
        this.positionCardClassCss="hide";
        this.separateCardClassCss="hide";
        this.assignmentCardClassCss = "structureCard";
        return "redirect:/structure";
    }

    //Position

    //Add new position if positionUpdate = false or update if positionUpdate = true
    @PostMapping("/add-update-position")
    public String createUpdatePosition(@RequestParam String title,
                                       @RequestParam String sortTitle,
                                       @RequestParam String position_role){
        if(isPositionUpdate){
            positionService.updatePosition(this.position.getId(), title, sortTitle, position_role);;
            this.isPositionUpdate = false;
            this.assignmentCardClassCss="hide";
            this.separateCardClassCss="hide";
            this.positionCardClassCss="hide";
        }else{
            Position newPosition = new Position(title, sortTitle, position_role);
            if(!positionService.isExistByTitleOrSortTitle(title, sortTitle)){
                positionService.createPosition(newPosition);
            }
            this.assignmentCardClassCss="hide";
            this.separateCardClassCss="hide";
            this.positionCardClassCss="hide";
        }
        return "redirect:/structure";
    }

    //delete position by id
    @GetMapping("/delete-position/{id}")
    public String deletePosition(@PathVariable("id") Long positionOrder){
        positionService.deletePosition(positionOrder);
        return "redirect:/structure";
    }

    //get position by id and save the position to this.position
    @GetMapping("/get-position-id/{id}")
    public String getPositionId(@PathVariable("id") Long positionId)
    {
        this.isPositionUpdate = true;
        this.position = positionService.getPositionById(positionId);
        this.assignmentCardClassCss="hide";
        this.separateCardClassCss="hide";
        this.positionCardClassCss= "structureCard";
        return "redirect:/structure";
    }

    @GetMapping("/is-position-update-false")
    public String isUpdatePositionFalse(){
        this.isPositionUpdate = false;
        this.assignmentCardClassCss="hide";
        this.separateCardClassCss="hide";
        this.positionCardClassCss = "structureCard";
        return "redirect:/structure";
    }

    //Separate

    @PostMapping("/add-update-separate")
    public String createSeparate(@RequestParam String title,
                                 @RequestParam String description){
        if(isSeparateUpdate){
            separateService.updateSeparateTitle(this.separate.getId(), title, description);
            this.isSeparateUpdate = false;
            this.assignmentCardClassCss="hide";
            this.positionCardClassCss="hide";
            this.separateCardClassCss="hide";
        }else{
            Separate newSeparate = new Separate(title, description);
            if(!separateService.isExistBySeparateTitle(title)){
                separateService.createSeparate(newSeparate);
            }
            this.assignmentCardClassCss="hide";
            this.positionCardClassCss="hide";
            this.separateCardClassCss="hide";
        }
        return "redirect:/structure";
    }


    @GetMapping("/delete-separate/{id}")
    public String deleteSeparate(@PathVariable("id") Long separateId){
        separateService.deleteSeparate(separateId);
        return "redirect:/structure";
    }

    @GetMapping("/get-separate-id/{id}")
    public String getSeparateId(@PathVariable("id") Long separateId)
    {
        this.isSeparateUpdate = true;
        this.separate = separateService.getSeparateById(separateId);
        this.assignmentCardClassCss="hide";
        this.positionCardClassCss="hide";
        this.separateCardClassCss = "structureCard";
        return "redirect:/structure";
    }

    @GetMapping("/is-separate-update-false")
    public String isUpdateSeparateFalse(){
        this.isSeparateUpdate = false;
        this.assignmentCardClassCss="hide";
        this.positionCardClassCss="hide";
        this.separateCardClassCss ="structureCard";
        return "redirect:/structure";
    }

    //Classes css
    @GetMapping("/close-structure-form")
    public String closeForm(){
        this.assignmentCardClassCss = "hide";
        this.positionCardClassCss = "hide";
        this.separateCardClassCss = "hide";
        return "redirect:/structure";
    }

}
