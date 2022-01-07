package com.example.budzikinteraktywny.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budzikinteraktywny.model.ButtonItem;

import java.util.ArrayList;
import java.util.List;

public class AddAlarmViewModel extends ViewModel {
    private MutableLiveData <List<ButtonItem>> items = new MutableLiveData<>();

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
}
