package com.example.budzikinteraktywny.DB.dbEntities;

import androidx.room.TypeConverter;

import java.util.Date;


//Converters swapping date to long to be able to keep the date in the db
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
