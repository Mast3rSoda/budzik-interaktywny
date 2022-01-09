package com.example.budzikinteraktywny.db;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.budzikinteraktywny.db.dao.AlarmGamesDao;
import com.example.budzikinteraktywny.db.dao.AlarmModelDao;
import com.example.budzikinteraktywny.db.dao.DatesDataModelDao;
import com.example.budzikinteraktywny.db.dao.DayOfTheWeekModelDao;
import com.example.budzikinteraktywny.db.dao.GameSettingsDao;
import com.example.budzikinteraktywny.db.entities.AlarmGames;
import com.example.budzikinteraktywny.db.entities.AlarmModel;
import com.example.budzikinteraktywny.db.entities.Converters;
import com.example.budzikinteraktywny.db.entities.DatesDataModel;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;
import com.example.budzikinteraktywny.db.entities.GameSettings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AlarmModel.class, DatesDataModel.class, DayOfTheWeekModel.class, AlarmGames.class, GameSettings.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class appDB extends RoomDatabase {
    public abstract AlarmModelDao AlarmModelDao();
    public abstract DatesDataModelDao DatesDataModelDao();
    public abstract DayOfTheWeekModelDao DayOfTheWeekModelDao();
    public abstract AlarmGamesDao AlarmGamesDao();
    public abstract GameSettingsDao GameSettingsDao();

    public static appDB instance;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(8);

    public static synchronized appDB getDatabase(final Context context) {
        if (instance == null) {
            synchronized (appDB.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            appDB.class, "database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                AlarmModelDao alarmModelDao = instance.AlarmModelDao();
                alarmModelDao.insert(new AlarmModel(1, 1, 12, 1, "a", "a", true, true));
            });
        }
    };
}
