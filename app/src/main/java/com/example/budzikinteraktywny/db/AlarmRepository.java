package com.example.budzikinteraktywny.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.budzikinteraktywny.db.dao.AlarmGamesDao;
import com.example.budzikinteraktywny.db.dao.AlarmModelDao;
import com.example.budzikinteraktywny.db.dao.DatesDataModelDao;
import com.example.budzikinteraktywny.db.dao.DayOfTheWeekModelDao;
import com.example.budzikinteraktywny.db.dao.GameSettingsDao;
import com.example.budzikinteraktywny.db.dbEntity.AlarmModel;

import java.util.List;

public class AlarmRepository {
    private AlarmGamesDao alarmGamesDao;
    private AlarmModelDao alarmModelDao;
    private DatesDataModelDao datesDataModelDao;
    private DayOfTheWeekModelDao dayOfTheWeekModelDao;
    private GameSettingsDao gameSettingsDao;

    private final LiveData<List<AlarmModel>> allAlarms;

    public AlarmRepository(Application application) {
        appDB db = appDB.getDatabase(application);
        alarmGamesDao = db.AlarmGamesDao();
        alarmModelDao = db.AlarmModelDao();
        datesDataModelDao = db.DatesDataModelDao();
        dayOfTheWeekModelDao = db.DayOfTheWeekModelDao();
        gameSettingsDao = db.GameSettingsDao();

        allAlarms = alarmModelDao.getAllOrdered();
    }

    //functions that can not run in the main thread

    public void alarmModelInsert(AlarmModel alarmModel) {
        appDB.databaseWriteExecutor.execute(() -> {
            alarmModelDao.insert(alarmModel);
        });
    }

    public void alarmModelDelete(AlarmModel alarmModel) {
        appDB.databaseWriteExecutor.execute(() -> {
            alarmModelDao.delete(alarmModel);
        });
    }

    public void deleteAllAlarms() {
        appDB.databaseWriteExecutor.execute(() -> {
            alarmModelDao.deleteAllAlarms();
        });
    }

    //other functions

    public LiveData<List<AlarmModel>> getAllAlarms() {
        return allAlarms;
    }

}
