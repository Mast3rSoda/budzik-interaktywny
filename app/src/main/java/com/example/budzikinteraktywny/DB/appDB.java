package com.example.budzikinteraktywny.DB;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

import kotlin.jvm.Volatile;

@Database(entities = {AlarmModel.class, DatesDataModel.class, DayOfTheWeekModel.class, AlarmGames.class, GameSettings.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class appDB extends RoomDatabase {
    public abstract AlarmModelDao AlarmModelDao();
    public abstract DatesDataModelDao DatesDataModelDao();
    public abstract DayOfTheWeekModelDao DayOfTheWeekModelDao();
    public abstract AlarmGamesDao AlarmGamesDao();
    public abstract GameSettingsDao GameSettingsDao();

    public static appDB instance;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static synchronized appDB getDatabase(final Context context) {
        if (instance == null) {
            synchronized (appDB.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            appDB.class, "database").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        private AlarmModelDao alarmModelDao = appDB.instance.AlarmModelDao();
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            appDB.databaseWriteExecutor.execute(() -> {
                alarmModelDao.insert(new AlarmModel(1, 1, "a", "a", true, true));
            });
        }
    };
}
