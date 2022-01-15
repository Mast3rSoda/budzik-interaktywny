package com.example.budzikinteraktywny.db.entities;


import android.app.PendingIntent;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AlarmSettings")
public class AlarmModel {
    @PrimaryKey(autoGenerate = true)
    private int alarmID;

    private int snoozeAmount;
    private int snoozeDuration;

    private int alarmHour;
    private int alarmMinute;

    private String alarmName;
    private String alarmRingtone;

    private boolean isOn;
    private boolean isVibrationOn;

    public AlarmModel(int snoozeAmount, int snoozeDuration, int alarmHour, int alarmMinute, String alarmName, String alarmRingtone, boolean isOn, boolean isVibrationOn) {
        this.snoozeAmount = snoozeAmount;
        this.snoozeDuration = snoozeDuration;
        this.alarmHour = alarmHour;
        this.alarmMinute = alarmMinute;
        this.alarmName = alarmName;
        this.alarmRingtone = alarmRingtone;
        this.isOn = isOn;
        this.isVibrationOn = isVibrationOn;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public int getSnoozeAmount() {
        return snoozeAmount;
    }

    public int getSnoozeDuration() {
        return snoozeDuration;
    }

    public int getAlarmHour() {
        return alarmHour;
    }

    public int getAlarmMinute() {
        return alarmMinute;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public String getAlarmRingtone() {
        return alarmRingtone;
    }

    public boolean isOn() {
        return isOn;
    }

    public boolean isVibrationOn() {
        return isVibrationOn;
    }
}
