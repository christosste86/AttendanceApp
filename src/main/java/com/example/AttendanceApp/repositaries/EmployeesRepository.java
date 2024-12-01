package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employees, Long> {

}
