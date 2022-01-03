package com.example.budzikinteraktywny.DB.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.budzikinteraktywny.DB.dbEntities.DayOfTheWeekModel;

@Dao
public interface DayOfTheWeekModelDao {

    @Insert
    void insert(DayOfTheWeekModel dayOfTheWeekModel);

    @Delete
    void delete(DayOfTheWeekModel dayOfTheWeekModel);

    @Update
    void update(DayOfTheWeekModel dayOfTheWeekModel);
}
