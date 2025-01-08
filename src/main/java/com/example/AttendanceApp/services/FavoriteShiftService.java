package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.FavoriteShift;
import com.example.AttendanceApp.models.Position;
import com.example.AttendanceApp.models.Role;

import java.time.LocalDateTime;
import java.util.List;

public interface FavoriteShiftService {
    List<FavoriteShift> getFavoriteShifts();

    FavoriteShift getFavoriteShiftById(long id);


    void createFavoriteShift(FavoriteShift favoriteShift);

    void updateFavoriteShift(long id, LocalDateTime StartShift, LocalDateTime EndShift);

    void deleteFavoriteShift(long id);
}
