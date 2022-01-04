package com.example.budzikinteraktywny;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.budzikinteraktywny.DB.appDB;
import com.example.budzikinteraktywny.DB.dbEntities.AlarmModel;
import com.example.budzikinteraktywny.alarmRecycler.AlarmAdapter;
import com.example.budzikinteraktywny.viewModels.AlarmViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlarmViewModel alarmViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView alarmRecyclerView = findViewById(R.id.alarmRecyclerView);
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        alarmRecyclerView.setHasFixedSize(true);

        AlarmAdapter alarmAdapter = new AlarmAdapter();
        alarmRecyclerView.setAdapter(alarmAdapter);

        alarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(this, alarmModels -> {
        alarmAdapter.setAlarms(alarmModels);
        });
    }
}