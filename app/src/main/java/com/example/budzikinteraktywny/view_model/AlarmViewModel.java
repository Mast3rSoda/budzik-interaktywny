package com.example.budzikinteraktywny.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budzikinteraktywny.db.AlarmRepository;
import com.example.budzikinteraktywny.db.dbEntity.AlarmModel;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {
    private final AlarmRepository repository;
    private final LiveData<List<AlarmModel>> allAlarms;


    public AlarmViewModel(@NonNull Application application) {
        super(application);
        repository = new AlarmRepository(application);
        allAlarms = repository.getAllAlarms();
    }

    public void alarmModelInsert(AlarmModel alarmModel) {
        repository.alarmModelInsert(alarmModel);
    }

    public void alarmModelDelete(AlarmModel alarmModel) {
        repository.alarmModelDelete(alarmModel);
    }

    public void deleteAllAlarms() {
        repository.deleteAllAlarms();
    }

    public LiveData<List<AlarmModel>> getAllAlarms() {
        return allAlarms;
    }
}
