package com.example.budzikinteraktywny.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.budzikinteraktywny.DB.dao.AlarmGamesDao;
import com.example.budzikinteraktywny.DB.dao.AlarmModelDao;
import com.example.budzikinteraktywny.DB.dao.DatesDataModelDao;
import com.example.budzikinteraktywny.DB.dao.DayOfTheWeekModelDao;
import com.example.budzikinteraktywny.DB.dao.GameSettingsDao;
import com.example.budzikinteraktywny.DB.dbEntities.AlarmModel;

import java.util.List;

public class AlarmRepository {
    private AlarmGamesDao alarmGamesDao;
    private AlarmModelDao alarmModelDao;
    private DatesDataModelDao datesDataModelDao;
    private DayOfTheWeekModelDao dayOfTheWeekModelDao;
    private GameSettingsDao gameSettingsDao;

    private LiveData<List<AlarmModel>> allAlarms;

    private AlarmRepository(Application application) {
        appDB db = appDB.getDatabase(application);
        alarmGamesDao = db.AlarmGamesDao();
        alarmModelDao = db.AlarmModelDao();
        datesDataModelDao = db.DatesDataModelDao();
        dayOfTheWeekModelDao = db.DayOfTheWeekModelDao();
        gameSettingsDao = db.GameSettingsDao();

        allAlarms = alarmModelDao.getAllOrdered();
    }

    //functions that can not run in the main thread

    void insert(AlarmModel alarmModel) {
        appDB.databaseWriteExecutor.execute(() -> {
            alarmModelDao.insert(alarmModel);
        });
    }

    //other functions

    LiveData<List<AlarmModel>> getAllAlarms() {
        return allAlarms;
    }

}
