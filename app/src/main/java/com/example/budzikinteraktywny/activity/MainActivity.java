package com.example.budzikinteraktywny.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.budzikinteraktywny.alarm_manager.AlarmHelper;
import com.example.budzikinteraktywny.R;
import com.example.budzikinteraktywny.adapter.AlarmAdapter;
import com.example.budzikinteraktywny.callbacks.OnCardClick;
import com.example.budzikinteraktywny.db.entities.AlarmModel;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;
import com.example.budzikinteraktywny.view_model.AlarmViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ALARM_DEFAULT_VALUE = 1;
    private AlarmViewModel alarmViewModel;
    public static final int RESULT_EDIT = 2;
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.SCHEDULE_EXACT_ALARM,
            Manifest.permission.SET_ALARM,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.VIBRATE,
            Manifest.permission.DISABLE_KEYGUARD,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.SYSTEM_ALERT_WINDOW
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        checkPermissions();

        RecyclerView alarmRecyclerView = findViewById(R.id.alarmRecyclerView);
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        alarmRecyclerView.setHasFixedSize(true);

        AlarmAdapter alarmAdapter = new AlarmAdapter();
        alarmRecyclerView.setAdapter(alarmAdapter);

        alarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);

//        Might use MediatorLiveData? Cause this is bad
//        But it works! Good programming practices c:
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
                int id = alarmAdapter.getAlarmAt(viewHolder.getAdapterPosition()).getAlarmID();
//                WOOOOOOO!! No more softlocked alarms xddddd
                cancelAlarm(id);
                alarmViewModel.alarmModelDelete(alarmAdapter.getAlarmAt(viewHolder.getAdapterPosition()));
                Snackbar.make(findViewById(R.id.relativeLayout), R.string.delete_alarm, Snackbar.LENGTH_SHORT).setAnchorView(fab).show();

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
                intent.putExtra(AddEditAlarmActivity.EXTRA_VALUES, getOnButtonValues(dayOfTheWeekModel));
                launcher.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Toast.makeText(this, R.string.add_alarm, Toast.LENGTH_SHORT).show();
                    Intent data = result.getData();
                    if (data == null) return;
                    String name = data.getStringExtra(AddEditAlarmActivity.EXTRA_NAME);
                    int hour = data.getIntExtra(AddEditAlarmActivity.EXTRA_HOUR, ALARM_DEFAULT_VALUE);
                    int minute = data.getIntExtra(AddEditAlarmActivity.EXTRA_MINUTE, ALARM_DEFAULT_VALUE);
                    boolean[] values = data.getBooleanArrayExtra(AddEditAlarmActivity.EXTRA_VALUES);
                    Calendar calendar = setCalendar(hour, minute, values);
//                    alarmViewModel.alarmModelInsert(new AlarmModel(1, 1, hour, minute, name, "e", true, true),
//                            alarmID -> ));
                    alarmViewModel.alarmModelInsert(new AlarmModel(1, 1, hour, minute, name, "e", true, true))
                            .observe(this, aLong -> {
                                alarmViewModel.dayOfTheWeekInsert(new DayOfTheWeekModel(aLong.intValue(), values[0], values[1], values[2], values[3], values[4], values[5], values[6]));
                                setAlarm(aLong.intValue(), calendar);
                            });
                } else if (result.getResultCode() == RESULT_EDIT) {
                    Toast.makeText(this, R.string.edit_alarm, Toast.LENGTH_SHORT).show();
                    Intent data = result.getData();
                    if (data == null) return;
                    int id = data.getIntExtra(AddEditAlarmActivity.EXTRA_ID, -1);
                    if (id == -1) return;
                    cancelAlarm(id);
                    String name = data.getStringExtra(AddEditAlarmActivity.EXTRA_NAME);
                    int hour = data.getIntExtra(AddEditAlarmActivity.EXTRA_HOUR, ALARM_DEFAULT_VALUE);
                    int minute = data.getIntExtra(AddEditAlarmActivity.EXTRA_MINUTE, ALARM_DEFAULT_VALUE);
                    boolean[] values = data.getBooleanArrayExtra(AddEditAlarmActivity.EXTRA_VALUES);
                    AlarmModel alarmModel = new AlarmModel(1, 1, hour, minute, name, "e", true, true);
                    alarmModel.setAlarmID(id);
                    alarmViewModel.alarmModelUpdate(alarmModel);
                    alarmViewModel.dayOfTheWeekUpdate(new DayOfTheWeekModel(id, values[0], values[1], values[2], values[3], values[4], values[5], values[6]));
                    Calendar calendar = setCalendar(hour, minute, values);
                    setAlarm(id, calendar);
                }

            }
    );


    //No idea why this won't show the permissions box.
    //From logcat => "Can request only one set of permissions at a time"
    //Checked if values are being parsed correctly, they are.
    //No idea why this isn't asking for permissions, app is working without them, so no worries
    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        for (final String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
            if (!missingPermissions.isEmpty()) {
                String[] permissions = missingPermissions.toArray(new String[missingPermissions.size()]);
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
            }
        }
    }
//    This one is not the one I need, but afaik is good
//    ActivityResultLauncher<String> getPerms = registerForActivityResult(new ActivityResultContracts.GetContent(),
//            new ActivityResultCallback<Uri>() {
//                @Override
//                public void onActivityResult(Uri result) {
//
//                }
//            });

    public void setIsOn(boolean b, int id) {
        alarmViewModel.updateIsOn(b, id);
    }


//    Afaik the setNextAlarm function should be able to do that no problem
//    Just gotta create the conditions in this func | In the end I've changed both
//    As none of them was correct <_<
//    But, alas.. It's finally working :D
    public Calendar setCalendar(int hour, int minute, boolean[] values) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        boolean[] temp = new boolean[7];
        temp[0] = values[6];
        System.arraycopy(values, 0, temp, 1, 6);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (temp[day - 1]) {
            if (calendar.after(Calendar.getInstance()))
                return calendar;
        }
        for (int i = day; i < 7; i++) {
            if (temp[i]) {
                if (i + 1 - day < 0)
                    i += 7;
                i = i + 1 - day;
                calendar.add(Calendar.DATE, i);
                return calendar;
            }
        }
        for (int i = 0; i < day - 1; i++) {
            if (temp[i]) {
                if (i + 1 - day < 0)
                    i += 7;
                i = i + 1 - day;
                calendar.add(Calendar.DATE, i);
                return calendar;
            }
        }
        if (temp[day - 1])
            calendar.add(Calendar.DATE, 7);
        if (calendar.before(Calendar.getInstance()) || calendar.equals(Calendar.getInstance()))
            calendar.add(Calendar.DATE, 1);
        return calendar;
    }

    public void setAlarm(int id, Calendar calendar) {
        AlarmHelper alarmHelper = new AlarmHelper();
        alarmHelper.setAlarm(id, this, calendar);
    }

    public void cancelAlarm(int id) {
        AlarmHelper alarmHelper = new AlarmHelper();
        alarmHelper.cancelAlarm(id, this);
    }

    public boolean[] getOnButtonValues(@NonNull DayOfTheWeekModel dayOfTheWeekModel) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                return true;
            case R.id.youtube:
                Uri uri = Uri.parse("https://youtube.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                return true;
            default:
                Toast.makeText(this, "nothing done", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }

}