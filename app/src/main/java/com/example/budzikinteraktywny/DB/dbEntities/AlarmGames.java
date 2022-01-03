package com.example.budzikinteraktywny.DB.dbEntities;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "AlarmGames", foreignKeys = {@ForeignKey(entity = AlarmModel.class,
                                                            parentColumns = "alarmID",
                                                            childColumns = "alarmID",
                                                            onDelete = CASCADE),
        @ForeignKey(entity = GameSettings.class,
                    parentColumns = "gameID",
                    childColumns = "gameID",
                    onDelete = CASCADE)}, indices = {@Index("gameID")})
public class AlarmGames {

    @PrimaryKey
    private int alarmID;

    private int gameID;

    private int gameDifficulty;

    public AlarmGames(int gameID, int gameDifficulty) {
        this.gameID = gameID;
        this.gameDifficulty = gameDifficulty;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public int getGameID() {
        return gameID;
    }

    public int getGameDifficulty() {
        return gameDifficulty;
    }
}
