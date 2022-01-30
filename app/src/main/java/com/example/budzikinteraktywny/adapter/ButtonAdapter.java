package com.example.budzikinteraktywny.adapter;


import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budzikinteraktywny.R;
import com.example.budzikinteraktywny.model.ButtonItem;
import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {
    private List<ButtonItem> items = new ArrayList<>();
    private final ButtonAdapterInteractions interactions;

    public ButtonAdapter(ButtonAdapterInteractions interactions) {
        this.interactions = interactions;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ButtonViewHolder(layoutInflater.
                inflate(R.layout.item_button, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        ButtonItem item = items.get(position);
        holder.button.setOnClickListener(view -> {
            interactions.onButtonClick(position);
        });
        holder.button.setText(item.getText());
        int currentNightMode = holder.button.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int color = 0;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active on device
                if (item.isEnabled())
                    color = ContextCompat.getColor(holder.itemView.getContext(), R.color.golden_tainoi);
                else
                    color = ContextCompat.getColor(holder.itemView.getContext(), R.color.white);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active on device
                if (item.isEnabled())
                    color = ContextCompat.getColor(holder.itemView.getContext(), R.color.gray);
                else
                    color = ContextCompat.getColor(holder.itemView.getContext(), R.color.charcoal);
                break;
        }
        holder.button.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder {
        private final Button button;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }

    public void setItems(List<ButtonItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }


}

