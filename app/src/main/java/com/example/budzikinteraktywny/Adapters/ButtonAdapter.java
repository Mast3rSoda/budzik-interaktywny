package com.example.budzikinteraktywny.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budzikinteraktywny.R;
import com.example.budzikinteraktywny.model.ButtonItem;

import java.util.ArrayList;
import java.util.List;

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
        return new ButtonViewHolder(layoutInflater.inflate(R.layout.item_button, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        ButtonItem item = items.get(position);
        holder.button.setOnClickListener(view -> {
          interactions.onButtonClick(position);
        });
        holder.button.setText(item.getText());
        if(item.isEnabled()) {
            int color = ContextCompat.getColor(holder.itemView.getContext(), R.color.purple_200);
            holder.button.setBackgroundColor(color);
        }
        else {
            int color = ContextCompat.getColor(holder.itemView.getContext(), R.color.salmon);
            holder.button.setBackgroundColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ButtonViewHolder extends RecyclerView.ViewHolder {
        Button button;

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

