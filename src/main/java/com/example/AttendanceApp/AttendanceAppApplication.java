package com.example.AttendanceApp;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Role;
import com.example.AttendanceApp.repositaries.EmployeeRepository;
import com.example.AttendanceApp.repositaries.PositionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class AttendanceAppApplication implements CommandLineRunner {

	private final EmployeeRepository employeeRepository;
	private final PositionRepository positionRepository;
	private final PasswordEncoder passwordEncoder;

    public AttendanceAppApplication(EmployeeRepository employeeRepository, PositionRepository positionRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {SpringApplication.run(AttendanceAppApplication.class, args); }

	@Override
	public void run(String... args) throws Exception {

		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");

		Position adminPosition = new Position();
		adminPosition.setTitle("Administrator");
		adminPosition.setSortTitle("ADMIN");
		adminPosition.setRole("ADMIN");
		if(positionRepository.findByTitle(adminPosition.getTitle()).isEmpty()) {
			positionRepository.save(adminPosition);
		}

		Employee administrator = new Employee();
		administrator.setFirstName("Christos");
		administrator.setLastName("Stefanakis");
		administrator.setUsername("admin");
		administrator.setPassword(passwordEncoder.encode("admin"));
		adminPosition.setEmployees(administrator);
		administrator.addRole(adminRole);
		if(employeeRepository.findEmployeesByUsername(administrator.getUsername()).isEmpty()) {
			employeeRepository.save(administrator);
		}
	}
}
