package com.example.android2.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android2.History;
import com.example.android2.HistorySource;
import com.example.android2.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final HistorySource dataSource;

    public HistoryAdapter(HistorySource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<History> histories = dataSource.getHistories();
        History history = histories.get(position);
        holder.tvId.setText(String.valueOf(history.id));
        holder.tvCity.setText(history.city);
        holder.tvDate.setText(history.date);
        holder.tvTemperature.setText(history.temperature);
    }

    @Override
    public int getItemCount() {
        return (int) dataSource.getCountHistories();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvCity;
        TextView tvDate;
        TextView tvTemperature;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
        }
    }
}
