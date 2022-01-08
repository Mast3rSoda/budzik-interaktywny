package com.example.budzikinteraktywny.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.budzikinteraktywny.db.dbEntity.DatesDataModel;

@Dao
public interface DatesDataModelDao {

    @Insert
    void insert(DatesDataModel... datesDataModel);

    @Delete
    void delete(DatesDataModel datesDataModel);

    @Update
    void update(DatesDataModel datesDataModel);
}
