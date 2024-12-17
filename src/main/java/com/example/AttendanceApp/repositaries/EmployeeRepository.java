package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.firstName like :firstName and e.lastName like :lastName and e.separatedName = :seperatedName and e.position = :position")
    List<Employee> filterEmployees(String firstName, String lastName, String seperatedName, String position);

    @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
    List<Employee> findEmployeesByFirstNameAndLastName(String firstName, String lastName);
}
