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
import android.widget.Toast;

import com.example.budzikinteraktywny.adapter.AlarmAdapter;
import com.example.budzikinteraktywny.callbacks.OnCardClick;
import com.example.budzikinteraktywny.db.entities.AlarmModel;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;
import com.example.budzikinteraktywny.view_model.AlarmViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    public static final int ALARM_DEFAULT_VALUE = 1;
    private AlarmViewModel alarmViewModel;
    public static final int RESULT_EDIT = 2;


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
            Intent intent = new Intent(MainActivity.this, AddEditAlarmActivity.class);
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

        alarmAdapter.setOnCardClickListener(new OnCardClick() {
            @Override
            public void onCardClick(AlarmModel alarmModel, DayOfTheWeekModel dayOfTheWeekModel) {
                Intent intent = new Intent(MainActivity.this, AddEditAlarmActivity.class);
                intent.putExtra(AddEditAlarmActivity.EXTRA_ID, alarmModel.getAlarmID());
                intent.putExtra(AddEditAlarmActivity.EXTRA_NAME, alarmModel.getAlarmName());
                intent.putExtra(AddEditAlarmActivity.EXTRA_HOUR, alarmModel.getAlarmHour());
                intent.putExtra(AddEditAlarmActivity.EXTRA_MINUTE, alarmModel.getAlarmMinute());
                intent.putExtra(AddEditAlarmActivity.EXTRA_VALUES, getOnButtons(dayOfTheWeekModel));
                launcher.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Toast.makeText(this, "Alarm added!", Toast.LENGTH_SHORT).show();
                    Intent data = result.getData();
                    if(data == null) return;
                    String name = data.getStringExtra(AddEditAlarmActivity.EXTRA_NAME);
                    int hour = data.getIntExtra(AddEditAlarmActivity.EXTRA_HOUR, ALARM_DEFAULT_VALUE);
                    int minute = data.getIntExtra(AddEditAlarmActivity.EXTRA_MINUTE, ALARM_DEFAULT_VALUE);
                    boolean[] values = data.getBooleanArrayExtra(AddEditAlarmActivity.EXTRA_VALUES);

//                    alarmViewModel.alarmModelInsert(new AlarmModel(1, 1, hour, minute, name, "e", true, true),
//                            alarmID -> ));
                    alarmViewModel.alarmModelInsert(new AlarmModel(1, 1, hour, minute, name, "e", true, true))
                            .observe(this, aLong -> {
                                alarmViewModel.dayOfTheWeekInsert(new DayOfTheWeekModel(aLong.intValue(), values[0], values[1], values[2], values[3], values[4], values[5], values[6]));
                    });
                } else if (result.getResultCode() == RESULT_EDIT) {
                    Toast.makeText(this, "Alarm edited!", Toast.LENGTH_SHORT).show();
                    Intent data = result.getData();
                    if (data == null) return;
                    int id = data.getIntExtra(AddEditAlarmActivity.EXTRA_ID, -1);
                    if (id == -1) return;
                    String name = data.getStringExtra(AddEditAlarmActivity.EXTRA_NAME);
                    int hour = data.getIntExtra(AddEditAlarmActivity.EXTRA_HOUR, ALARM_DEFAULT_VALUE);
                    int minute = data.getIntExtra(AddEditAlarmActivity.EXTRA_MINUTE, ALARM_DEFAULT_VALUE);
                    boolean[] values = data.getBooleanArrayExtra(AddEditAlarmActivity.EXTRA_VALUES);
                    AlarmModel alarmModel = new AlarmModel(1, 1, hour, minute, name, "e", true, true);
                    alarmModel.setAlarmID(id);
                    alarmViewModel.alarmModelUpdate(alarmModel);
                    alarmViewModel.dayOfTheWeekUpdate(new DayOfTheWeekModel(id, values[0], values[1], values[2], values[3], values[4], values[5], values[6]));
                }
            }
    );

    public void setIsOn(boolean b, int id) {
        alarmViewModel.updateIsOn(b, id);
    }

    public boolean[] getOnButtons(@NonNull DayOfTheWeekModel dayOfTheWeekModel) {
        boolean[] values = new boolean[7];
        values[0] = dayOfTheWeekModel.getMonday();
        values[1] = dayOfTheWeekModel.getTuesday();
        values[2] = dayOfTheWeekModel.getWednesday();
        values[3] = dayOfTheWeekModel.getThursday();
        values[4] = dayOfTheWeekModel.getFriday();
        values[5] = dayOfTheWeekModel.getSaturday();
        values[6] = dayOfTheWeekModel.getSunday();

        return values;
    }


}