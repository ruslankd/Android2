package com.example.android2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.android2.weather.WeatherData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class Settings {
    private static Settings instance = null;
    private static final String TAG = "WEATHER";

    private Settings() {}

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    private boolean darkTheme = false;
    private double currT;
    private String[] cities = new String[]{
            "Moscow",
            "Saint Petersburg",
            "Yekaterinburg",
            "Sochi",
            "Vladivostok"
    };
    private int[][] temperatures = {
            {18, 18, 18, 15, 13, 12, 10},
            {14, 15, 14, 11, 14, 13, 12},
            {15, 16, 17, 14, 14, 14, 13},
            {22, 25, 26, 26, 27, 27, 26},
            {18, 14, 15, 15, 14, 16, 15}
    };
    private int currentIndexOfCity = 0;

    public boolean isDarkThemeFlag() {
        return darkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
    }

    public String[] getCities() {
        return cities;
    }

    public int getCurrentIndexOfCity() {
        return currentIndexOfCity;
    }

    public void setCurrentIndexOfCity(int currentIndexOfCity) {
        this.currentIndexOfCity = currentIndexOfCity;
    }

    public String getTemperature() {
        calcTemp();
        double temp = currT - 273.15;
        return (((temp > 0) ? "+" : "") + String.format(Locale.US,"%.2f", temp) + "°");
    }

    private void calcTemp() {
        try {
            final URL uri = new URL("https://api.openweathermap.org/data/2.5/weather?q=" +
                    cities[getCurrentIndexOfCity()] +
                    ",RU&appid=" + BuildConfig.WEATHER_API_KEY);
            final Handler handler = new Handler(Looper.getMainLooper());

            new Thread(() -> {
                HttpsURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpsURLConnection) uri.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000);

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String result = getLines(in);

                    Gson gson = new Gson();
                    final WeatherData weatherData = gson.fromJson(result, WeatherData.class);
                    handler.post(() -> currT = weatherData.getMain().getTemp());
                } catch (Exception e) {
                    Log.e(TAG, "Fail connection", e);
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
