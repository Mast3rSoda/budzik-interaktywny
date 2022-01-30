package com.example.budzikinteraktywny.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budzikinteraktywny.R;
import com.example.budzikinteraktywny.adapter.ButtonAdapter;
import com.example.budzikinteraktywny.adapter.ButtonAdapterInteractions;
import com.example.budzikinteraktywny.view_model.AddAlarmViewModel;

public class AddEditAlarmActivity extends AppCompatActivity implements ButtonAdapterInteractions {
    public static final String EXTRA_ID =
            "com.example.budzikinteraktywny.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.budzikinteraktywny.EXTRA_NAME";
    public static final String EXTRA_VALUES =
            "com.example.budzikinteraktywny.EXTRA_VALUES";
    public static final String EXTRA_HOUR =
            "com.example.budzikinteraktywny.EXTRA_HOUR";
    public static final String EXTRA_MINUTE =
            "com.example.budzikinteraktywny.EXTRA_MINUTE";

    public static final int DEFAULT_VALUE = 1;


    private TimePicker timePicker;
    private EditText editTextAlarmName;
    private boolean firstLoad = true;
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

        Intent intent = getIntent();

        viewModel = new ViewModelProvider(this).get(AddAlarmViewModel.class);
        viewModel.getItems().observe(this, buttonItems -> {
                    if (intent.hasExtra(EXTRA_ID) && firstLoad) {
                        for (int i = 0; i < 7; i++) {
                            buttonItems.get(i).setEnabled(intent.getBooleanArrayExtra(EXTRA_VALUES)[i]);
                        }
                        firstLoad = false;
                    }
                    buttonAdapter.setItems(buttonItems);

                }
        );
        if (intent.hasExtra(EXTRA_ID)) {
            timePicker.setHour(intent.getIntExtra(EXTRA_HOUR, DEFAULT_VALUE));
            timePicker.setMinute(intent.getIntExtra(EXTRA_MINUTE, DEFAULT_VALUE));
            editTextAlarmName.setText(intent.getStringExtra(EXTRA_NAME));
        }
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
            title = this.getResources().getString(R.string.defaultAlarmName);
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, title);
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MINUTE, minute);
        intent.putExtra(EXTRA_VALUES, values);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
            setResult(MainActivity.RESULT_EDIT, intent);
            finish();
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
