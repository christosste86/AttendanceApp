package com.example.AttendanceApp.repositaries;

import com.example.AttendanceApp.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("select p from positions p where p.title = :title")
    Optional<Position> findByTitle(@Param("title") String title);

    @Query("select p from positions p where p.title = :title or p.sortTitle = :sortTitle")
    Optional<Position> findByTitleOrSortTitle(String title, String sortTitle);
}
