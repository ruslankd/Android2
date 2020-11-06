package com.example.android2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.ViewHolder> {

    private int[] dataSource;

    public TemperatureAdapter(int[] dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temperature,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String s = ((dataSource[position] > 0) ? "+" : "") + dataSource[position] + "°";
        holder.getTextView().setText(s);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTemperature);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
