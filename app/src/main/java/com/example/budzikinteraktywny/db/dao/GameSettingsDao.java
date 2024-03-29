package com.example.budzikinteraktywny.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.budzikinteraktywny.db.entities.GameSettings;

@Dao
public interface GameSettingsDao {

    @Insert
    void insert(GameSettings gameSettings);

    @Delete
    void delete(GameSettings gameSettings);

    @Update
    void update(GameSettings gameSettings);
}
