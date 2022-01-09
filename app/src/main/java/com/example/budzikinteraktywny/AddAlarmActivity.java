package com.example.budzikinteraktywny;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budzikinteraktywny.adapter.ButtonAdapter;
import com.example.budzikinteraktywny.adapter.ButtonAdapterInteractions;
import com.example.budzikinteraktywny.view_model.AddAlarmViewModel;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddAlarmActivity extends AppCompatActivity implements ButtonAdapterInteractions {
    public static final String EXTRA_NAME =
            "com.example.budzikinteraktywny.EXTRA_NAME";
    public static final String EXTRA_VALUES =
            "com.example.budzikinteraktywny.EXTRA_VALUES";
    public static final String EXTRA_HOUR =
            "com.example.budzikinteraktywny.EXTRA_HOUR";
    public static final String EXTRA_MINUTE =
            "com.example.budzikinteraktywny.EXTRA_MINUTE";


    private TimePicker timePicker;
    private EditText editTextAlarmName;
    ButtonAdapter buttonAdapter = new ButtonAdapter(this);
    AddAlarmViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_layout);

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        Button button = findViewById(R.id.saveButton);
        button.setOnClickListener(view -> saveAlarm());

        editTextAlarmName = findViewById(R.id.editAlarmName);
        RecyclerView recyclerView = findViewById(R.id.buttonLayout);
        recyclerView.setAdapter(buttonAdapter);

        viewModel = new ViewModelProvider(this).get(AddAlarmViewModel.class);
        viewModel.getItems().observe(this, buttonItems -> buttonAdapter.setItems(buttonItems));

    }

    @Override
    public void onButtonClick(int pos) {
        viewModel.toggleItem(pos);
    }

    public void saveAlarm() {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String title = editTextAlarmName.getText().toString();
        boolean[] values = viewModel.getButtonValues();

        if (title.trim().isEmpty()) {
            title = "Alarm";
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, title);
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MINUTE, minute);
        intent.putExtra(EXTRA_VALUES, values);
        setResult(RESULT_OK, intent);
        finish();
    }
}
