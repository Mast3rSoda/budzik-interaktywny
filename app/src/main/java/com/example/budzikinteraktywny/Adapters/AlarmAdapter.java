package com.example.budzikinteraktywny.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budzikinteraktywny.DB.dbEntities.AlarmModel;
import com.example.budzikinteraktywny.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.alarmViewHolder> {
    private List<AlarmModel> alarms = new ArrayList<>();
    @NonNull
    @Override
    public alarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new alarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull alarmViewHolder holder, int position) {
        AlarmModel currentAlarm = alarms.get(position);
        holder.alarmTime.setText(new StringBuilder().append(String.valueOf(currentAlarm.getAlarmHour())).append(":").append(String.valueOf(currentAlarm.getAlarmMinute())));
        holder.alarmName.setText(currentAlarm.getAlarmName());
        holder.onOffSwitch.setChecked(currentAlarm.isOn());
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<AlarmModel> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    class alarmViewHolder extends RecyclerView.ViewHolder {
        private TextView alarmTime;
        private TextView alarmName;
        private TextView daysOfWeekSelected;
        private SwitchCompat onOffSwitch;

        public alarmViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.alarmTime);
            alarmName = itemView.findViewById(R.id.alarmName);
            daysOfWeekSelected = itemView.findViewById(R.id.daysOfWeekSelected);
            onOffSwitch = itemView.findViewById(R.id.onOffSwitch);
        }

    }

}
