package com.example.android2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHistory(History history);

    @Delete
    void deleteHistory(History history);

    @Query("select * from history")
    List<History> getAllHistory();

    @Query("select count() from history")
    long getCountHistory();

    @Query("DELETE FROM history WHERE id = :id")
    void deleteHistoryById(long id);
}
