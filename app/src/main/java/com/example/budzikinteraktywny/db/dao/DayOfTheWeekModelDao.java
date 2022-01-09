package com.example.budzikinteraktywny.db.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.budzikinteraktywny.db.entities.AlarmModel;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;

import java.util.List;

@Dao
public interface DayOfTheWeekModelDao {

    @Insert
    void insert(DayOfTheWeekModel dayOfTheWeekModel);

    @Delete
    void delete(DayOfTheWeekModel dayOfTheWeekModel);

    @Update
    void update(DayOfTheWeekModel dayOfTheWeekModel);

    @Query("SELECT * From DayOfTheWeekData ORDER BY alarmID DESC")
    LiveData<List<DayOfTheWeekModel>> getAllDaysOrdered();
}
