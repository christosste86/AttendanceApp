package com.example.AttendanceApp;

import com.example.AttendanceApp.models.*;
import com.example.AttendanceApp.repositaries.AssignmentRepository;
import com.example.AttendanceApp.repositaries.EmployeeRepository;
import com.example.AttendanceApp.repositaries.FavoriteShiftRepository;
import com.example.AttendanceApp.repositaries.PositionRepository;
import com.example.AttendanceApp.services.AssignmentService;
import com.example.AttendanceApp.services.EmployeesService;
import com.example.AttendanceApp.services.FavoriteShiftService;
import com.example.AttendanceApp.services.PositionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class AttendanceAppApplication implements CommandLineRunner {

	private final EmployeeRepository employeeRepository;
	private final PositionRepository positionRepository;
	private final AssignmentRepository assignmentRepository;
	private final PasswordEncoder passwordEncoder;
	private final FavoriteShiftRepository favoriteShiftRepository;

	public AttendanceAppApplication(EmployeeRepository employeeRepository, PositionRepository positionRepository, AssignmentRepository assignmentRepository, PasswordEncoder passwordEncoder, FavoriteShiftRepository favoriteShiftRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.assignmentRepository = assignmentRepository;
        this.passwordEncoder = passwordEncoder;
		this.favoriteShiftRepository = favoriteShiftRepository;
	}


    public static void main(String[] args) {SpringApplication.run(AttendanceAppApplication.class, args); }

	@Override
	public void run(String... args) throws Exception {

		//create assignment
		Assignment fullTime = new Assignment("Full time", 40);

		//check if Assignment not exist and create it
		if(assignmentRepository.findByHoursPerWeek(fullTime.getHoursPerWeek()).isEmpty()){
			assignmentRepository.save(fullTime);
		}

		//create role
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");

		//create position
		Position adminPosition = new Position();
		adminPosition.setTitle("Administrator");
		adminPosition.setSortTitle("ADMIN");
		adminPosition.setRole("ADMIN");
		//check if position not exist and save it
		if(positionRepository.findByTitle(adminPosition.getTitle()).isEmpty()) {
			positionRepository.save(adminPosition);
		}

		//create Administrator position
		Employee administrator = new Employee();
		administrator.setFirstName("Administrator");
		administrator.setLastName("Administrator");
		administrator.setUsername("admin");
		administrator.setPassword(passwordEncoder.encode("admin"));
		administrator.setPosition(positionRepository.findByTitle(adminPosition.getTitle()).get());
		administrator.addRole(adminRole);
		administrator.setAssignment(assignmentRepository.findByHoursPerWeek(fullTime.getHoursPerWeek()).get());

		//check if Administrator not exist and save it
		if(employeeRepository.findEmployeeByUsername(administrator.getUsername()).isEmpty()) {
			employeeRepository.save(administrator);
		}


		//create three empty Favorite Shifts form Morning, Afternoon, Night
		FavoriteShift morning = new FavoriteShift();
		morning.setShiftName("Morning");
		FavoriteShift afternoon = new FavoriteShift();
		afternoon.setShiftName("Afternoon");
		FavoriteShift night = new FavoriteShift();
		night.setShiftName("Night");

		//check if Favorite shifts not exist and save it
		if(favoriteShiftRepository.findById(1L).isEmpty()){
			favoriteShiftRepository.save(morning);
		}
		if(favoriteShiftRepository.findById(2L).isEmpty()){
			favoriteShiftRepository.save(afternoon);
		}

		if(favoriteShiftRepository.findById(3L).isEmpty()){
			favoriteShiftRepository.save(night);
		}
	}
}
