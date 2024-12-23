package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Separate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeparateRepository extends JpaRepository<Separate, Long> {
    @Query("select s from separates s where s.id = :id")
    List<Separate> findBySeparateId(@Param("id") Long id);
}
