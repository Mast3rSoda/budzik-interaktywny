package com.example.budzikinteraktywny.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budzikinteraktywny.db.AlarmRepository;
import com.example.budzikinteraktywny.db.SimplerCallback;
import com.example.budzikinteraktywny.db.entities.AlarmModel;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {
    private final AlarmRepository repository;
    private final LiveData<List<AlarmModel>> allAlarms;
    private final LiveData<List<DayOfTheWeekModel>> allDays;


    public AlarmViewModel(@NonNull Application application) {
        super(application);
        repository = new AlarmRepository(application);
        allAlarms = repository.getAllAlarms();
        allDays = repository.getAllDays();
    }
//
//    public void alarmModelInsert(AlarmModel alarmModel, SimpleCallback<Long> callback) {
//        repository.alarmModelInsert(alarmModel, callback);
//    }

    public LiveData<Long> alarmModelInsert(AlarmModel alarmModel) {
        return repository.alarmModelInsert(alarmModel);
    }

    public void alarmModelDelete(AlarmModel alarmModel) {
        repository.alarmModelDelete(alarmModel);
    }

    public void deleteAllAlarms() {
        repository.deleteAllAlarms();
    }

    public void dayOfTheWeekInsert(DayOfTheWeekModel dayOfTheWeekModel) {
        repository.dayOfTheWeekInsert(dayOfTheWeekModel);
    }

    public void dayOfTheWeekDelete(DayOfTheWeekModel dayOfTheWeekModel) {
        repository.dayOfTheWeekDelete(dayOfTheWeekModel);
    }

    public LiveData<List<AlarmModel>> getAllAlarms() {
        return allAlarms;
    }

    public LiveData<List<DayOfTheWeekModel>> getAllDays() {
        return allDays;
    }
}
