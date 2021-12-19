package com.example.budzikinteraktywny.DB.dbEntities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GameSettings")
public class GameSettings {

    @PrimaryKey(autoGenerate = true)
    public int gameID;
}
