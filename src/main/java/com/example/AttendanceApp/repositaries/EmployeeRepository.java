package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Role;
import com.example.AttendanceApp.models.Separate;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where e.username = :username")
    Optional<Employee> findEmployeeByUsername(String username);

    @Query("select e from Employee e order by e.position.title")
    List<Employee> findEmployeesOrderByPositionTitle();

    @Query("select e from Employee e where e.separate = :separate order by e.position.title")
    List<Employee> findEmployeesBySeparateOrderByPositionTitle(Separate separate);

    @Query("select e from Employee e where e.separate = :separate order by e.firstName")
    List<Employee> findEmployeesBySeparateOrderByFirstName(Separate separate);

    @Query("select e from Employee e where " +
            "e.firstName like :firstName and " +
            "e.lastName like :lastName and " +
            "e.separate = :separate and " +
            "e.position = :position")
    List<Employee> filterEmployeesByFirstNameLastNameSeparatePosition(
            String firstName,
            String lastName,
            Separate separate,
            Position position);

    @Query("select e from Employee e where " +
            "e.firstName like :firstName and " +
            "e.lastName like :lastName and " +
            "e.separate = :separate")
    List<Employee> filterEmployeesByFirstNameLastNameSeparate(
            String firstName,
            String lastName,
            Separate separate);

    @Query("select e from Employee e where " +
            "e.firstName like :firstName and " +
            "e.lastName like :lastName and " +
            "e.position = :position")
    List<Employee> filterEmployeesByFirstNameLastNamePosition(
            String firstName,
            String lastName,
            Position position);

    @Query("select e from Employee e where " +
            "e.firstName like :firstName and " +
            "e.lastName like :lastName")
    List<Employee> filterEmployeesByFirstNameLastName(
            String firstName,
            String lastName);

}