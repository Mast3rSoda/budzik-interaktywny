package com.example.budzikinteraktywny.callbacks;

import com.example.budzikinteraktywny.db.entities.AlarmModel;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;

public interface OnCardClick {
    void onCardClick(AlarmModel alarmModel, DayOfTheWeekModel dayOfTheWeekModel);
}
