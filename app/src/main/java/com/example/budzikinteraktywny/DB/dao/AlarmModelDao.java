package com.example.budzikinteraktywny.DB.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.budzikinteraktywny.DB.dbEntities.AlarmModel;

import java.util.List;

@Dao
public interface AlarmModelDao {

    @Insert()
    void insert(AlarmModel alarmModel);

    @Delete
    void delete(AlarmModel alarmModel);

    @Update
    void update(AlarmModel alarmModel);

    @Query("SELECT * From AlarmSettings ORDER BY alarmID DESC")
    LiveData<List<AlarmModel>> getAllOrdered();

}
