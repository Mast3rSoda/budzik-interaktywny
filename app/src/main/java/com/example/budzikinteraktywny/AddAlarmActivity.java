package com.example.budzikinteraktywny;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budzikinteraktywny.Adapters.ButtonAdapter;
import com.example.budzikinteraktywny.Adapters.ButtonAdapterInteractions;
import com.example.budzikinteraktywny.ViewModels.AddAlarmViewModel;
import com.example.budzikinteraktywny.ViewModels.AlarmViewModel;
import com.example.budzikinteraktywny.model.ButtonItem;

import java.util.List;

public class AddAlarmActivity extends AppCompatActivity implements ButtonAdapterInteractions {
    private TimePicker timePicker;
    private EditText editTextAlarmName;
    private RecyclerView recyclerView;
    ButtonAdapter buttonAdapter = new ButtonAdapter(this);
    AddAlarmViewModel viewModel;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_layout);

        timePicker = findViewById(R.id.timePicker);
        editTextAlarmName = findViewById(R.id.editAlarmName);
        recyclerView = findViewById(R.id.buttonLayout);
        recyclerView.setAdapter(buttonAdapter);

        viewModel = new ViewModelProvider(this).get(AddAlarmViewModel.class);
        viewModel.getItems().observe(this, buttonItems -> {
            buttonAdapter.setItems(buttonItems);
        });

    }

    @Override
    public void onButtonClick(int pos) {
        viewModel.toggleItem(pos);
    }
}
