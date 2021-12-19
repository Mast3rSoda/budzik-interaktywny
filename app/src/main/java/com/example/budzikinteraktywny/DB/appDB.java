package com.example.budzikinteraktywny.DB;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.budzikinteraktywny.DB.dao.AlarmGamesDao;
import com.example.budzikinteraktywny.DB.dao.AlarmModelDao;
import com.example.budzikinteraktywny.DB.dao.DatesDataModelDao;
import com.example.budzikinteraktywny.DB.dao.DayOfTheWeekModelDao;
import com.example.budzikinteraktywny.DB.dao.GameSettingsDao;
import com.example.budzikinteraktywny.DB.dbEntities.AlarmGames;
import com.example.budzikinteraktywny.DB.dbEntities.AlarmModel;
import com.example.budzikinteraktywny.DB.dbEntities.Converters;
import com.example.budzikinteraktywny.DB.dbEntities.DatesDataModel;
import com.example.budzikinteraktywny.DB.dbEntities.DayOfTheWeekModel;
import com.example.budzikinteraktywny.DB.dbEntities.GameSettings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AlarmModel.class, DatesDataModel.class, DayOfTheWeekModel.class, AlarmGames.class, GameSettings.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class appDB extends RoomDatabase {
    public abstract AlarmModelDao AlarmModelDao();
    public abstract DatesDataModelDao DatesDataModelDao();
    public abstract DayOfTheWeekModelDao DayOfTheWeekModelDao();
    public abstract AlarmGamesDao AlarmGamesDao();
    public abstract GameSettingsDao GameSettingsDao();

    public static volatile appDB instance;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static appDB getDatabase(final Context context) {
        if (instance == null) {
            synchronized (appDB.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            appDB.class, "database")
                            .build();
                }
            }
        }
        return instance;
    }
}
