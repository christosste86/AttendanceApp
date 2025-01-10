package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Separate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeparateRepository extends JpaRepository<Separate, Long> {
    @Query("select s from separates s where s.title = :title")
    Optional<Separate> findByTitle(@Param("title") String title);
}
