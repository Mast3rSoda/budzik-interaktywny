package com.example.budzikinteraktywny.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budzikinteraktywny.model.ButtonItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddAlarmViewModel extends ViewModel {
    private final MutableLiveData <List<ButtonItem>> items = new MutableLiveData<>();

    public AddAlarmViewModel() {
        List<ButtonItem> list = new ArrayList<>();
        list.add(new ButtonItem("M",false));
        list.add(new ButtonItem("T",false));
        list.add(new ButtonItem("W",false));
        list.add(new ButtonItem("T",false));
        list.add(new ButtonItem("F",false));
        list.add(new ButtonItem("S",false));
        list.add(new ButtonItem("S",false));
        items.postValue(list);
    }

    public LiveData<List<ButtonItem>> getItems() {
        return items;
    }

    public void toggleItem(int pos) {
        List<ButtonItem> list = items.getValue();
        if (list != null) {
            ButtonItem item = list.get(pos);
            item.setEnabled(!item.isEnabled());
            items.postValue(list);
        }

    }

    public boolean[] getButtonValues() {
        boolean[] values = new boolean[7];
        for (int i = 0; i < 7; i++) {
            values[i] =  Objects.requireNonNull(items.getValue()).get(i).isEnabled();
        }
        return values;
    }

}
