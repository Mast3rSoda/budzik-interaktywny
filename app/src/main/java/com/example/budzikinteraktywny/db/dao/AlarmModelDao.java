package com.example.budzikinteraktywny.db.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.budzikinteraktywny.db.entities.AlarmModel;

import java.util.List;

@Dao
public interface AlarmModelDao {

    @Insert
    long insert(AlarmModel alarmModel);

    @Delete
    void delete(AlarmModel alarmModel);

    @Update
    void update(AlarmModel alarmModel);

    @Query("DELETE FROM AlarmSettings")
    void deleteAllAlarms();

    @Query("SELECT * From AlarmSettings ORDER BY alarmID DESC")
    LiveData<List<AlarmModel>> getAllOrdered();

    @Query("UPDATE AlarmSettings SET isOn = :isOn WHERE alarmID = :id")
    void updateIsOn(boolean isOn, int id);

}
