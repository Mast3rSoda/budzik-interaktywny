package com.example.budzikinteraktywny.db.dbEntity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GameSettings")
public class GameSettings {

    @PrimaryKey(autoGenerate = true)
    private int gameID;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
