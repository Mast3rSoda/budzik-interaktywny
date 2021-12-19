package com.example.budzikinteraktywny.DB.dbEntities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "DayOfTheWeekData", foreignKeys = @ForeignKey(entity = AlarmModel.class, parentColumns = "alarmID", childColumns = "alarmID", onDelete = CASCADE))
public class DayOfTheWeekModel {
    @PrimaryKey
    public int alarmID;

    public Boolean monday;
    public Boolean tuesday;
    public Boolean wednesday;
    public Boolean thursday;
    public Boolean friday;
    public Boolean saturday;
    public Boolean sunday;

}
