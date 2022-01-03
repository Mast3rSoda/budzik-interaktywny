package com.example.budzikinteraktywny.DB.dbEntities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AlarmSettings")
public class AlarmModel {
    @PrimaryKey(autoGenerate = true)
    private int alarmID;

    private int snoozeAmount;
    private int snoozeDuration;

    private String alarmName;
    private String alarmRingtone;

    private boolean isOn;
    private boolean isVibrationOn;

    public AlarmModel(int snoozeAmount, int snoozeDuration, String alarmName, String alarmRingtone, boolean isOn, boolean isVibrationOn) {
        this.snoozeAmount = snoozeAmount;
        this.snoozeDuration = snoozeDuration;
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
