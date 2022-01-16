package com.example.budzikinteraktywny.db;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.budzikinteraktywny.db.dao.AlarmGamesDao;
import com.example.budzikinteraktywny.db.dao.AlarmModelDao;
import com.example.budzikinteraktywny.db.dao.DatesDataModelDao;
import com.example.budzikinteraktywny.db.dao.DayOfTheWeekModelDao;
import com.example.budzikinteraktywny.db.dao.GameSettingsDao;
import com.example.budzikinteraktywny.db.entities.AlarmModel;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;

import java.util.List;

public class AlarmRepository {
    private AlarmGamesDao alarmGamesDao;
    private AlarmModelDao alarmModelDao;
    private DatesDataModelDao datesDataModelDao;
    private DayOfTheWeekModelDao dayOfTheWeekModelDao;
    private GameSettingsDao gameSettingsDao;

    private final LiveData<List<AlarmModel>> allAlarms;
    private final LiveData<List<DayOfTheWeekModel>> allDays;

    public AlarmRepository(Application application) {
        appDB db = appDB.getDatabase(application);
        alarmGamesDao = db.AlarmGamesDao();
        alarmModelDao = db.AlarmModelDao();
        datesDataModelDao = db.DatesDataModelDao();
        dayOfTheWeekModelDao = db.DayOfTheWeekModelDao();
        gameSettingsDao = db.GameSettingsDao();

        allAlarms = alarmModelDao.getAllOrdered();
        allDays = dayOfTheWeekModelDao.getAllDaysOrdered();
    }

    //functions that can not run in the main thread

//    public void alarmModelInsert(AlarmModel alarmModel, SimpleCallback<Long> simpleCallback) {
//        appDB.databaseWriteExecutor.execute(() -> {
//            long alarmID = alarmModelDao.insert(alarmModel);
//            simpleCallback.onValue(alarmID);
//        });
//    }
    public LiveData<Long> alarmModelInsert(AlarmModel alarmModel) {
        final MutableLiveData<Long> liveData = new MutableLiveData<>();
        appDB.databaseWriteExecutor.execute(() -> {
            long alarmID = alarmModelDao.insert(alarmModel);
            liveData.postValue(alarmID);
        });
        return liveData;
    }

    public void alarmModelUpdate(AlarmModel alarmModel) {
        appDB.databaseWriteExecutor.execute(() -> {
            alarmModelDao.update(alarmModel);
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

    public void updateIsOn(boolean isOn, int id) {
        appDB.databaseWriteExecutor.execute(() -> {
            alarmModelDao.updateIsOn(isOn, id);
        });
    }

    public void dayOfTheWeekInsert(DayOfTheWeekModel dayOfTheWeekModel) {
        appDB.databaseWriteExecutor.execute(() -> {
            dayOfTheWeekModelDao.insert(dayOfTheWeekModel);
        });
    }

    public void dayOfTheWeekDelete(DayOfTheWeekModel dayOfTheWeekModel) {
        appDB.databaseWriteExecutor.execute(() -> {
            dayOfTheWeekModelDao.delete(dayOfTheWeekModel);
        });
    }

    public void dayOfTheWeekUpdate(DayOfTheWeekModel dayOfTheWeekModel) {
        appDB.databaseWriteExecutor.execute(() -> {
            dayOfTheWeekModelDao.update(dayOfTheWeekModel);
        });
    }


    //other functions
    public LiveData<DayOfTheWeekModel> getAlarmDays(int id) {
        return dayOfTheWeekModelDao.getAlarmDays(id);
    }
    public LiveData<List<AlarmModel>> getAllAlarms() {
        return allAlarms;
    }
    public LiveData<List<DayOfTheWeekModel>> getAllDays() {
        return allDays;
    }

}

