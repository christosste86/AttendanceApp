package com.example.AttendanceApp.controllers;


import com.example.AttendanceApp.models.Assignment;
import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;
import com.example.AttendanceApp.services.AssignmentService;
import com.example.AttendanceApp.services.EmployeesService;
import com.example.AttendanceApp.services.PositionService;
import com.example.AttendanceApp.services.SeparateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeesController {

    private final EmployeesService employeesService;
    private final SeparateService separateService;
    private final PositionService positionService;
    private final AssignmentService assignmentService;
    private List<Employee> employeesList = new ArrayList<>();
    private String firstName;
    private String lastName;
    private Separate separate;
    private Position position;

    @Autowired
    public EmployeesController(EmployeesService employeesService, SeparateService separateService, PositionService positionService, AssignmentService assignmentService) {
        this.employeesService = employeesService;
        this.separateService = separateService;
        this.positionService = positionService;
        this.assignmentService = assignmentService;
    }

    public List<Employee> getEmployeesList(){
        return this.employeesList;
    }

    @GetMapping("/employees")
    public String employeesPage(Model model){
        model.addAttribute("separatedList", separateService.getSeparates());
        model.addAttribute("positionsList", positionService.getPositions());
        model.addAttribute("assignmentsList", assignmentService.getAssignments());
        model.addAttribute("employees", employeesService.getEmployeesList(firstName, lastName, separate, position));
        return "employees";
    }

    @GetMapping("/add-employee")
    public String getEmployeesForm(Model model){
        model.addAttribute("separates", separateService.getSeparates());
        model.addAttribute("positions", positionService.getPositions());
        model.addAttribute("assignments", assignmentService.getAssignments());
        return "employees";
    }

    @PostMapping("/add-employee")
    public String createEmployee(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam Separate separate,
                                 @RequestParam Position position,
                                 @RequestParam Assignment assignment,
                                 @RequestParam Double paymentPerHour,
                                 @RequestParam String username,
                                 @RequestParam String password){
        System.out.println("Received Data: First Name = " + firstName + ", Last Name = " + lastName);
        Employee employee = new Employee(firstName, lastName, username, password, paymentPerHour);
        employee.setPosition(position);
        employee.setAssignment(assignment);
        employee.setSeparate(separate);
        if(employeesService.isExist(firstName, lastName)){
            employeesService.saveEmployee(employee);
        }
        return "redirect:/employees";
    }



    @GetMapping("/employee-filter/")
    public String getEmployeeFilter(Model model,
                                    @RequestParam("firstName") String firstName,
                                    @RequestParam("lastName") String lastName,
                                    @RequestParam("separatedName") Separate separate,
                                    @RequestParam("position") Position position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.separate = separate;
        this.position = position;
        this.employeesList = employeesService.filteredEmployees(this.firstName, this.lastName, this.separate, this.position);
        model.addAttribute("filteredEmployees", this.employeesList);
        return "redirect:/";
    }

    @GetMapping("/about-employee/{employee}")
    public String aboutEmployeePage(Model model, @PathVariable("employee") Long employeeOrder){
        model.addAttribute("employees", employeesService.getEmployeeById(employeeOrder));
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable("id") Long employeeOrder){
        employeesService.deleteById(employeeOrder);
        return "redirect:/employees";
    }
}
