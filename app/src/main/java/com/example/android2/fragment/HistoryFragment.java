package com.example.android2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android2.App;
import com.example.android2.History;
import com.example.android2.HistoryDao;
import com.example.android2.HistoryDatabase;
import com.example.android2.HistorySource;
import com.example.android2.R;
import com.example.android2.Settings;
import com.example.android2.adapters.HistoryAdapter;

public class HistoryFragment extends Fragment {

    RecyclerView rvHistory;
    HistorySource historySource;
    HistoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvHistory = view.findViewById(R.id.rvHistory);

        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initRecyclerView() {
        rvHistory.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHistory.setLayoutManager(layoutManager);

//        HistoryDao historyDao = App
//                .getInstance()
//                .getHistoryDao();
        HistoryDatabase db = Room
                .databaseBuilder(
                        getContext(),
                        HistoryDatabase.class,
                        "history_database"
                )
                .allowMainThreadQueries()
                .build();
        historySource = new HistorySource(db.getHistoryDao());

        adapter = new HistoryAdapter(historySource);
        rvHistory.setAdapter(adapter);
    }

    public void addHistory(History history) {
        historySource.addHistory(history);
        adapter.notifyDataSetChanged();
    }
}