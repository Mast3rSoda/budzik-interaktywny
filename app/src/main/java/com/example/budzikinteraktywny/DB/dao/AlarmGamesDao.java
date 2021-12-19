package com.example.budzikinteraktywny.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.budzikinteraktywny.DB.dbEntities.AlarmGames;

@Dao
public interface AlarmGamesDao {

    @Insert
    void insert(AlarmGames... alarmGames);

    @Delete
    void delete(AlarmGames alarmGames);
}
