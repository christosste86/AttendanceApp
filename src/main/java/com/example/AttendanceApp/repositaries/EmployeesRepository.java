package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT COUNT(e) > 0 from Employees e WHERE e.firstName = :firstname AND e.lastName = :lastname")
    boolean isExist(@Param("firstname") String firstName, @Param("lastname") String lastName);

}
