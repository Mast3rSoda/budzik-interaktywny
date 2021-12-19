package com.example.budzikinteraktywny.DB.dbEntities;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "DatesData", foreignKeys = @ForeignKey(entity = AlarmModel.class, parentColumns = "alarmID", childColumns = "alarmID", onDelete = CASCADE))
public class DatesDataModel {
    @PrimaryKey
    public int alarmID;


    public Date alarmDate;


}

