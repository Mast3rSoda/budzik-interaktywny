package com.example.budzikinteraktywny;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmWakeActivity extends AppCompatActivity {

    private Button alarmWakeButtonDismiss;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_wake);

        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        TextView alarmWakeTime = findViewById(R.id.alarmWakeTime);
        alarmWakeButtonDismiss = findViewById(R.id.alarmWakeButtonDismiss);

        alarmWakeTime.setText(setAlarmTime());
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
        );
        mediaPlayer = MediaPlayer.create(this, R.raw.get_up_ringtone);
        mediaPlayer.start();

        alarmWakeButtonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //TODO Make the alarm last throughout the mini-game, not pause on button press
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public String setAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(calendar.getTime());
    }
}
