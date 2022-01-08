package com.example.budzikinteraktywny.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.budzikinteraktywny.db.dbEntity.DayOfTheWeekModel;

@Dao
public interface DayOfTheWeekModelDao {

    @Insert
    void insert(DayOfTheWeekModel dayOfTheWeekModel);

    @Delete
    void delete(DayOfTheWeekModel dayOfTheWeekModel);

    @Update
    void update(DayOfTheWeekModel dayOfTheWeekModel);
}
