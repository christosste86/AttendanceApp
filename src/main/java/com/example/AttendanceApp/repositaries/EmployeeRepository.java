package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.firstName like :firstName and e.lastName like :lastName and e.separate = :separate and e.position = :position")
    List<Employee> filterEmployees(String firstName, String lastName, Separate separate, Position position);

    @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
    List<Employee> findEmployeesByFirstNameAndLastName(String firstName, String lastName);
}
