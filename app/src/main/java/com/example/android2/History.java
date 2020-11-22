package com.example.android2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class History {

    public final static String ID = "id";
    public final static String CITY = "city";
    public final static String DATE = "date";
    public final static String TEMPERATURE = "temperature";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;

    @ColumnInfo(name = CITY)
    public String city;

    @ColumnInfo(name = DATE)
    public String date;

    @ColumnInfo(name = TEMPERATURE)
    public String temperature;
}
