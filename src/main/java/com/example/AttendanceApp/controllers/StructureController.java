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
    //Form
    private String assignmentFormCardCss = "hide";
    private String positionFormCardCss = "hide";
    private String separateFormCardCss = "hide";
    //Table
    private String assignmentTableCardCss = "structureCard";
    private String positionTableCardCss = "hide";
    private String separateTableCardCss = "hide";
    //side navigation
    private String assignmentNavigationOption = "selectedOption";
    private String positionNavigationOption = null;
    private String separateNavigationOption = null;

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
        //Form
        model.addAttribute("assignmentFormCardCss", this.assignmentFormCardCss);
        model.addAttribute("positionFormCardCss", this.positionFormCardCss);
        model.addAttribute("separateFormCardCss", this.separateFormCardCss);
        //Table
        model.addAttribute("assignmentTableCardCss", this.assignmentTableCardCss);
        model.addAttribute("positionTableCardCss", this.positionTableCardCss);
        model.addAttribute("separateTableCardCss", this.separateTableCardCss);
        //side navigation
        model.addAttribute("assignmentNavigationOption", this.assignmentNavigationOption);
        model.addAttribute("positionNavigationOption", this.positionNavigationOption);
        model.addAttribute("separateNavigationOption", this.separateNavigationOption);
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
            closeAllForms();
        }else{
            Assignment newAssignment = new Assignment(assignmentTitle, assignment);
            if(!assignmentService.isExist(assignmentTitle, assignment)){
                assignmentService.createAssignment(newAssignment);
            }
            closeAllForms();
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
        closeAllForms();
        this.assignmentFormCardCss="structureFormCard";
        return "redirect:/structure";
    }

    @GetMapping("/is-assignment-update-false")
    public String isUpdateAssignmentFalse(){
        this.isAssignmentUpdate = false;
        closeAllForms();
        this.assignmentFormCardCss = "structureFormCard";
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
            closeAllForms();
        }else{
            Position newPosition = new Position(title, sortTitle, position_role);
            if(!positionService.isExistByTitleOrSortTitle(title, sortTitle)){
                positionService.createPosition(newPosition);
            }
            closeAllForms();
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
        closeAllForms();
        this.positionFormCardCss= "structureFormCard";
        return "redirect:/structure";
    }

    @GetMapping("/is-position-update-false")
    public String isUpdatePositionFalse(){
        this.isPositionUpdate = false;
        closeAllForms();
        this.positionFormCardCss = "structureFormCard";
        return "redirect:/structure";
    }

    //Separate

    @PostMapping("/add-update-separate")
    public String createSeparate(@RequestParam String title,
                                 @RequestParam String description){
        if(isSeparateUpdate){
            separateService.updateSeparateTitle(this.separate.getId(), title, description);
            this.isSeparateUpdate = false;
            closeAllForms();
        }else{
            Separate newSeparate = new Separate(title, description);
            if(!separateService.isExistBySeparateTitle(title)){
                separateService.createSeparate(newSeparate);
            }
            closeAllForms();
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
        closeAllForms();
        this.separateFormCardCss = "structureFormCard";
        return "redirect:/structure";
    }

    @GetMapping("/is-separate-update-false")
    public String isUpdateSeparateFalse(){
        this.isSeparateUpdate = false;
        closeAllForms();
        this.separateFormCardCss ="structureFormCard";
        return "redirect:/structure";
    }

    //Classes css

    //Close all Forms
    @GetMapping("/close-structure-forms")
    public String closeAllForms(){
        this.assignmentFormCardCss = "hide";
        this.positionFormCardCss = "hide";
        this.separateFormCardCss = "hide";
        return "redirect:/structure";
    }

    //Close all Table
    @GetMapping("/close-structure-tables")
    public String closeAllTables(){
        this.positionTableCardCss = "hide";
        this.separateTableCardCss = "hide";
        this.assignmentTableCardCss = "hide";
        return "redirect:/structure";
    }

    //NavigationBarLeft
    @GetMapping("hide-all-side-nav-options")
    public String hideAllSideNavOptions(){
        this.assignmentTableCardCss = null;
        this.positionTableCardCss = null;
        this.separateTableCardCss = null;
        return "redirect:/structure";
    }

    @GetMapping("/open-assignment-table")
    public String openAssignmentTable(){
        hideAllSideNavOptions();
        this.assignmentNavigationOption = "selectedOption";
        //close all Forms
        closeAllForms();
        //close all Tables
        closeAllTables();
        //open Assignment table
        this.assignmentTableCardCss = "structureCard";

        return "redirect:/structure";
    }

    @GetMapping("/open-position-table")
    public String openPositionTable(){
        hideAllSideNavOptions();
        this.positionNavigationOption = "selectedOption";
        //close all Forms
        closeAllForms();
        //close all Tables
        closeAllTables();
        //open Position table
        this.positionTableCardCss = "structureCard";

        return "redirect:/structure";
    }

    @GetMapping("/open-separate-table")
    public String openSeparateTable(){
        hideAllSideNavOptions();
        this.separateNavigationOption = "selectedOption";
        //close all Forms
        closeAllForms();
        //close all Tables
        closeAllTables();
        //open Position table
        this.separateTableCardCss = "structureCard";

        return "redirect:/structure";
    }

}
