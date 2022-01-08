package com.example.budzikinteraktywny.db.dbEntity;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "DatesData", foreignKeys = @ForeignKey(entity = AlarmModel.class, parentColumns = "alarmID", childColumns = "alarmID", onDelete = CASCADE))
public class DatesDataModel {
    @PrimaryKey
    private int alarmID;

    private Date alarmDate;

    public DatesDataModel(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }
}

