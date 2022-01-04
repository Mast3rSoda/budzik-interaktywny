package com.example.budzikinteraktywny;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.budzikinteraktywny.DB.appDB;
import com.example.budzikinteraktywny.DB.dbEntities.AlarmModel;
import com.example.budzikinteraktywny.viewModels.AlarmViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlarmViewModel alarmViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(this, alarmModels -> {
        Toast.makeText(MainActivity.this, "xd", Toast.LENGTH_SHORT).show();
        });
    }
}