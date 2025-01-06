package com.example.AttendanceApp.controllers;


import com.example.AttendanceApp.models.*;
import com.example.AttendanceApp.services.*;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final BenefitCardService benefitCardService;
    private final PasswordEncoder passwordEncoder;
    private boolean isUpdate = false;
    private List<Employee> employeesList = new ArrayList<>();
    private Long employeeId;
    private String firstName;
    private String lastName;
    private BenefitCard benefitCardSn;
    private Separate separate;
    private Position position;
    private Assignment assignment;
    private Double paymentPerHour;
    private String username;
    private String password;

    @Autowired
    public EmployeesController(EmployeesService employeesService, SeparateService separateService, PositionService positionService, AssignmentService assignmentService, BenefitCardService benefitCardService, PasswordEncoder passwordEncoder) {
        this.employeesService = employeesService;
        this.separateService = separateService;
        this.positionService = positionService;
        this.assignmentService = assignmentService;
        this.benefitCardService = benefitCardService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Employee> getEmployeesList(){
        return this.employeesList;
    }

    @GetMapping("/employees")
    public String employeesPage(Model model){
        model.addAttribute("separatedList", separateService.getSeparates());
        model.addAttribute("positionsList", positionService.getPositions());
        model.addAttribute("assignmentsList", assignmentService.getAssignments());
        model.addAttribute("employees", employeesService.getEmployeesList());
        model.addAttribute("employeeId", this.employeeId);
        model.addAttribute("firstName" , this.firstName);
        model.addAttribute("lastName" , this.lastName);
        model.addAttribute("benefitCardSn" , this.benefitCardSn);
        model.addAttribute("separate" , this.separate);
        model.addAttribute("position" , this.position);
        model.addAttribute("assignment" , this.assignment);
        model.addAttribute("paymentPerHour" , this.paymentPerHour);
        model.addAttribute("username" , this.username);
        model.addAttribute("password" , this.password);
        model.addAttribute("isUpdate" , this.isUpdate);
        model.addAttribute("loginUserRole", employeesService.getLoginEmployee().getRoles().stream().toList().getFirst().toString());
        model.addAttribute("loginUserUsername", employeesService.getLoginEmployee().getUsername());
        return "employees";
    }

    @GetMapping("/add-employee")
    public String getEmployeesForm(Model model){
        model.addAttribute("separates", separateService.getSeparates());
        model.addAttribute("positions", positionService.getPositions());
        model.addAttribute("assignments", assignmentService.getAssignments());
        return "employees";
    }

    @PostMapping("/add-update-employee")
    public String createEmployee(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String benefitCardSn,
                                 @RequestParam Separate separate,
                                 @RequestParam Position position,
                                 @RequestParam Assignment assignment,
                                 @RequestParam Double paymentPerHour,
                                 @RequestParam String username,
                                 @RequestParam String password){
        BenefitCard benefitCard = new BenefitCard();
        benefitCard.setSerialNumber(benefitCardSn);
        benefitCard.setPoints(0);

        if(!benefitCardService.isExist(benefitCard.getSerialNumber())){
            benefitCardService.createBenefitCard(benefitCard);
        }

        if(!isUpdate){
            Employee employee = new Employee(firstName, lastName, username, passwordEncoder.encode(password), paymentPerHour);
            //create if benefit card serial number exist

            employee.setPosition(position);
            employee.addRole(new Role(position.getRole()));
            employee.setAssignment(assignment);
            employee.setSeparate(separate);
            employee.setBenefitCard(benefitCard);
            if(!employeesService.isExist(username)){
                employeesService.saveEmployee(employee);
            }
        }else{
            employeesService.updateEmployeeById(
                    this.employeeId,
                    firstName,
                    lastName,
                    benefitCard,
                    separate,
                    position,
                    assignment,
                    paymentPerHour,
                    username,
                    passwordEncoder.encode(password));
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
        this.employeesList = employeesService.getFilteredEmployeesList(this.firstName, this.lastName, this.separate, this.position);
        model.addAttribute("filteredEmployees", this.employeesList);
        return "redirect:/";
    }

    @GetMapping("/about-employee/{employee}")
    public String aboutEmployeePage(Model model, @PathVariable("employee") Long employeeOrder){
        model.addAttribute("employeeId", employeesService.getEmployeeById(employeeOrder));
        return "redirect:/employees";
    }

    @GetMapping("/get-employee-id-set-isUpdate-as-true/{id}")
    public String getEmployeeId(@PathVariable("id") Long employeeOrder){
        this.employeeId = employeeOrder;
        Employee employee = employeesService.getEmployeeById(employeeOrder);
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.benefitCardSn = employee.getBenefitCard();
        this.separate = employee.getSeparate();
        this.position = employee.getPosition();
        this.assignment = employee.getAssignment();
        this.paymentPerHour = employee.getPaymentPerHour();
        this.username = employee.getUsername();
        this.password = employee.getPassword();
        this.isUpdate = true;
        return "redirect:/employees";
    }

    @GetMapping("/set-isUpdate-as-False")
    public String setIsUpdateAsFalse(){
        this.isUpdate = false;
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable("id") Long employeeOrder){
        employeesService.deleteById(employeeOrder);
        return "redirect:/employees";
    }

}
