package com.example.budzikinteraktywny;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.budzikinteraktywny.DB.appDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDB db = appDB.getDatabase(this);

    }
}