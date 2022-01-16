package com.example.budzikinteraktywny;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.budzikinteraktywny.db.AlarmRepository;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Calendar;

public class AlarmWakeActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_wake);


        //Wooo! we got the alarm id.
        int id = getIntent().getIntExtra(AlarmReceiver.EXTRA_ID, -1);


        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        TextView alarmWakeTime = findViewById(R.id.alarmWakeTime);
        alarmWakeTime.setText(setAlarmTime());
        Button alarmWakeButtonDismiss = findViewById(R.id.alarmWakeButtonDismiss);

        setVolumeControlStream(AudioAttributes.USAGE_ALARM);
        mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://"+ getPackageName()+"/"+R.raw.get_up_ringtone));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(true);

//        Had some issues with a popping sound during the first ~10 seconds of the ringtone.
//        Turns out you shouldn't put the mediaPlayer.start() in a try/catch. Good to know.
        mediaPlayer.start();

        alarmWakeButtonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setNextAlarm(id);
    }

    //On that note, onDestroy should last if we use fragments, right?
    //TODO Make the alarm last throughout the mini-game, not pause on button press
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public String setAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(calendar.getTime());
    }

    public void setNextAlarm(int id) {
        AlarmRepository alarmRepository = new AlarmRepository(this.getApplication());
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        alarmRepository.getAlarmDays(id).observe(this, days -> {
            boolean[] values = getDayValues(days);
            for (int i = 0; i < 7; i++) {
                if (day != i + 1) {
                    if (values[i]) {
                        if (i + 1 - day < 0)
                            i += 7;
                        i = i + 1 - day;
                        calendar.add(Calendar.DATE, i);
                        setAlarm(id, calendar);
                        return;
                    }
                }
            }
            if (values[day - 1]) {
                calendar.add(Calendar.DATE, 7);
                setAlarm(id, calendar);
            }
            else {
                alarmRepository.updateIsOn(false, id);
            }
        });
    }

    public boolean[] getDayValues(DayOfTheWeekModel days) {
        boolean[] values = new boolean[7];

        if (days.getSunday())
            values[0] = true;
        if (days.getMonday())
            values[1] = true;
        if (days.getTuesday())
            values[2] = true;
        if (days.getWednesday())
            values[3] = true;
        if (days.getThursday())
            values[4] = true;
        if (days.getFriday())
            values[5] = true;
        if (days.getSaturday())
            values[6] = true;
        return values;
    }

    public void setAlarm(int id, Calendar calendar) {
        AlarmHelper alarmHelper = new AlarmHelper();
        alarmHelper.setAlarm(id, this, calendar);
    }

}
