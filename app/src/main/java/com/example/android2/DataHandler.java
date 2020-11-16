package com.example.android2;

import com.example.android2.weather.WeatherData;
import com.google.gson.Gson;

public class DataHandler {
    private final String data;

    public DataHandler(String data) {
        this.data = data;
    }

    public WeatherData getWeatherData() {
        Gson gson = new Gson();
        return gson.fromJson(data, WeatherData.class);
    }
}
