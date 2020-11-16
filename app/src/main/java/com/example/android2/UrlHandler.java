package com.example.android2;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class UrlHandler {
    private static final String TAG = "WEATHER";

    private final String city;

    public UrlHandler(String city) {
        this.city = city;
    }

    public String getData() throws MalformedURLException {
        final URL uri = new URL("https://api.openweathermap.org/data/2.5/weather?q=" +
                city +
                ",RU&appid=" + BuildConfig.WEATHER_API_KEY);

        HttpsURLConnection urlConnection = null;
        String result = null;
        try {
            urlConnection = (HttpsURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            result = getLines(in);

//                Gson gson = new Gson();
//                final WeatherData weatherData = gson.fromJson(result, WeatherData.class);
//                handler.post(() -> currT = weatherData.getMain().getTemp());
        } catch (Exception e) {
            Log.e(TAG, "Fail connection", e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
