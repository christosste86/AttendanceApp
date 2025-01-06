package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Employee;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Separate;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.firstName like :firstName and e.lastName like :lastName and e.separate = :separate and e.position = :position")
    List<Employee> filterEmployees(String firstName, String lastName, Separate separate, Position position);

    @Query("select e from Employee e where e.username = :username")
    List<Employee> findEmployeesByUsername(String username);

    Optional<Employee> findEmployeeByUsername(String username);

    @Query("select e from Employee e where e.separate = :separate order by e.position.sortTitle")
    List<Employee> findEmployeesBySeparate(Separate separate);

}
