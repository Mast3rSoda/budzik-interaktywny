package com.example.budzikinteraktywny.alarm_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.budzikinteraktywny.activity.AlarmWakeActivity;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String EXTRA_ID = "com.example.budzikinteraktywny.ar.id";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AlarmWakeActivity.class);
        i.putExtra(EXTRA_ID, intent.getIntExtra(AlarmHelper.EXTRA_ID, -1));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        context.startActivity(i);
    }
}
