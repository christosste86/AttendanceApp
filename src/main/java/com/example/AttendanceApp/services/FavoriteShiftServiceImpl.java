package com.example.AttendanceApp.services;

import com.example.AttendanceApp.models.FavoriteShift;
import com.example.AttendanceApp.models.Schedule;
import com.example.AttendanceApp.repositaries.FavoriteShiftRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteShiftServiceImpl implements FavoriteShiftService {

    private final FavoriteShiftRepository favoriteShiftRepository;

    public FavoriteShiftServiceImpl(FavoriteShiftRepository favoriteShiftRepository) {
        this.favoriteShiftRepository = favoriteShiftRepository;
    }

    @Override
    public List<FavoriteShift> getFavoriteShifts() {
        return favoriteShiftRepository.findAll();
    }

    @Override
    public FavoriteShift getFavoriteShiftById(long id) {
        Optional<FavoriteShift> favoriteShift = favoriteShiftRepository.findById(id);
        if(favoriteShift.isPresent()) {
            return favoriteShift.get();
        }return null;
    }

    @Override
    public void createFavoriteByTimesShift(Schedule schedule) {
        if(schedule.getShiftStart().getHour() >= 5 && schedule.getShiftEnd().getHour() <= 16){
            deleteFavoriteShift(1);
            createFavoriteShift(getFavoriteShiftById(1));
        }
        else if (schedule.getShiftStart().getHour() >= 11 && schedule.getShiftEnd().getHour() <= 23){
            deleteFavoriteShift(2);
            createFavoriteShift(getFavoriteShiftById(2));
        }
        else {
            deleteFavoriteShift(3);
            createFavoriteShift(getFavoriteShiftById(3));
        }
    }


    @Override
    public void createFavoriteShift(FavoriteShift favoriteShift) {
        favoriteShiftRepository.save(favoriteShift);
    }

    @Override
    public void updateFavoriteShift(long id, LocalDateTime StartShift, LocalDateTime EndShift) {
        Optional<FavoriteShift> favoriteShift = favoriteShiftRepository.findById(id);
        if(favoriteShift.isPresent()) {
            favoriteShift.get().setShiftStart(StartShift.toLocalTime());
            favoriteShift.get().setShiftEnd(EndShift.toLocalTime());
            favoriteShiftRepository.save(favoriteShift.get());
        }
    }

    @Override
    public void deleteFavoriteShift(long id) {
        Optional<FavoriteShift> favoriteShift = favoriteShiftRepository.findById(id);
        if(favoriteShift.isPresent()) {
            favoriteShiftRepository.delete(favoriteShift.get());
        }
    }
}
