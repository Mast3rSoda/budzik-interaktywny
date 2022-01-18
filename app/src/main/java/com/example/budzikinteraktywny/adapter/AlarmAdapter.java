package com.example.budzikinteraktywny.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budzikinteraktywny.AlarmHelper;
import com.example.budzikinteraktywny.MainActivity;
import com.example.budzikinteraktywny.callbacks.OnCardClick;
import com.example.budzikinteraktywny.db.AlarmRepository;
import com.example.budzikinteraktywny.db.dao.AlarmGamesDao;
import com.example.budzikinteraktywny.db.dao.AlarmModelDao;
import com.example.budzikinteraktywny.db.entities.AlarmModel;
import com.example.budzikinteraktywny.R;
import com.example.budzikinteraktywny.db.entities.DayOfTheWeekModel;
import com.example.budzikinteraktywny.view_model.AlarmViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private static List<AlarmModel> alarms = new ArrayList<>();
    private static List<DayOfTheWeekModel> days = new ArrayList<>();
    private static OnCardClick cardClickListener;

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new AlarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        AlarmModel currentAlarm = alarms.get(position);
        DayOfTheWeekModel currentDay = days.get(position);
        String hour = String.valueOf(currentAlarm.getAlarmHour());
        hour = ("00" + hour).substring(hour.length());
        String minute = String.valueOf(currentAlarm.getAlarmMinute());
        minute = ("00" + minute).substring(minute.length());
        holder.alarmTime.setText(new StringBuilder().append(hour).append(":").append(minute));
        holder.alarmName.setText(currentAlarm.getAlarmName());
        holder.onOffSwitch.setChecked(currentAlarm.isOn());
        holder.onOffSwitch.setOnClickListener(view -> {
            ((MainActivity) view.getContext()).setIsOn(!currentAlarm.isOn(), currentAlarm.getAlarmID());
            Calendar calendar = ((MainActivity) view.getContext()).setCalendar(currentAlarm.getAlarmHour(), currentAlarm.getAlarmMinute());
            if(currentAlarm.isOn())
                ((MainActivity) view.getContext()).cancelAlarm(currentAlarm.getAlarmID(), calendar);
            else
                ((MainActivity) view.getContext()).setAlarm(currentAlarm.getAlarmID(), calendar);
        });
        holder.daysOfWeekSelected.setText(getRepeatDays(currentDay));
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<AlarmModel> alarms, List<DayOfTheWeekModel> days) {
        AlarmAdapter.alarms = alarms;
        AlarmAdapter.days = days;
        notifyDataSetChanged();
    }

    public AlarmModel getAlarmAt(int position) {
        return alarms.get(position);
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {
        private final TextView alarmTime;
        private final TextView alarmName;
        private final TextView daysOfWeekSelected;
        private final SwitchCompat onOffSwitch;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.alarmTime);
            alarmName = itemView.findViewById(R.id.alarmName);
            daysOfWeekSelected = itemView.findViewById(R.id.daysOfWeekSelected);
            onOffSwitch = itemView.findViewById(R.id.onOffSwitch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (cardClickListener != null && position != RecyclerView.NO_POSITION)
                        cardClickListener.onCardClick(alarms.get(position), days.get(position));
                }
            });
        }

    }
    //bruh
    //Jesus, why can't I really find a better way to do it?
    //Update... Found a better way to do it!
    private String getRepeatDays(DayOfTheWeekModel dayOfTheWeekModel) {
        List<String> values = new ArrayList<>();
        if (dayOfTheWeekModel.getMonday())
            values.add("Mon");
        if (dayOfTheWeekModel.getTuesday())
            values.add("Tue");
        if (dayOfTheWeekModel.getWednesday())
            values.add("Wed");
        if (dayOfTheWeekModel.getThursday())
            values.add("Thu");
        if (dayOfTheWeekModel.getFriday())
            values.add("Fri");
        if (dayOfTheWeekModel.getSaturday())
            values.add("Sat");
        if (dayOfTheWeekModel.getSunday())
            values.add("Sun");
        return String.valueOf(values).replaceAll("[\\[\\]]", "");
    }

    public void setOnCardClickListener(OnCardClick cardClickListener) {
        AlarmAdapter.cardClickListener = cardClickListener;
    }

}
