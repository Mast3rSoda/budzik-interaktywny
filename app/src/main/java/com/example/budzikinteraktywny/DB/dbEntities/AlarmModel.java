package com.example.budzikinteraktywny.DB.dbEntities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AlarmSettings")
public class AlarmModel {
    @PrimaryKey(autoGenerate = true)
    public int alarmID;

    public int snoozeAmount;
    public int snoozeDuration;

    public String alarmName;
    public String alarmRingtone;

    public boolean isOn;
    public boolean isVibrationOn;


}
