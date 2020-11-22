package com.example.android2;

import android.app.Application;

import androidx.room.Room;

import com.example.android2.HistoryDatabase;

public class App extends Application {

    private static App instance;
    private HistoryDatabase db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        db = Room
                .databaseBuilder(
                        getApplicationContext(),
                        HistoryDatabase.class,
                        "history_database"
                )
                .allowMainThreadQueries()
                .build();
    }

    public HistoryDao getHistoryDao() {
        return db.getHistoryDao();
    }

}
