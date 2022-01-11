package com.example.budzikinteraktywny;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.budzikinteraktywny.adapter.AlarmAdapter;
import com.example.budzikinteraktywny.db.entities.AlarmModel;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;
import com.example.budzikinteraktywny.view_model.AlarmViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_ALARM_RESULT = 1;
    public static final int ALARM_DEFAULT_VALUE = 1;
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

        //Might use MediatorLiveData? Cause this is so fucking ghetto
        alarmViewModel.getAllAlarms().observe(this, alarmModels -> {
            alarmViewModel.getAllDays().observe(this, allDays -> {
                if (alarmModels.size() == allDays.size())
                    alarmAdapter.setAlarms(alarmModels, allDays);
            });
        });

        FloatingActionButton fab = findViewById(R.id.addAlarmButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddAlarmActivity.class);
            launcher.launch(intent);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                alarmViewModel.alarmModelDelete(alarmAdapter.getAlarmAt(viewHolder.getAdapterPosition()));
                Snackbar.make(findViewById(R.id.relativeLayout), "Alarm Deleted!", Snackbar.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(alarmRecyclerView);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Toast.makeText(this, "Alarm added!", Toast.LENGTH_SHORT).show();
                    Intent data = result.getData();
                    if(data == null) return;
                    String name = data.getStringExtra(AddAlarmActivity.EXTRA_NAME);
                    int hour = data.getIntExtra(AddAlarmActivity.EXTRA_HOUR, ALARM_DEFAULT_VALUE);
                    int minute = data.getIntExtra(AddAlarmActivity.EXTRA_MINUTE, ALARM_DEFAULT_VALUE);
                    boolean[] values = data.getBooleanArrayExtra(AddAlarmActivity.EXTRA_VALUES);

//                    alarmViewModel.alarmModelInsert(new AlarmModel(1, 1, hour, minute, name, "e", true, true),
//                            alarmID -> ));
                    alarmViewModel.alarmModelInsert(new AlarmModel(1, 1, hour, minute, name, "e", true, true))
                            .observe(this, aLong -> {
                                alarmViewModel.dayOfTheWeekInsert(new DayOfTheWeekModel(Math.toIntExact(aLong), values[0], values[1], values[2], values[3], values[4], values[5], values[6]));
                    });
                }
            }
    );

    public void setIsOn(boolean b, int id) {
        alarmViewModel.updateIsOn(b, id);
    }


}