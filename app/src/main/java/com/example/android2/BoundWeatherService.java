package com.example.android2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.net.MalformedURLException;

public class BoundWeatherService extends Service {

    private final IBinder binder = new WeatherServiceBinder();

    private double currDoubleTemp = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void calcTemp() {
        try {
            Settings settings = Settings.getInstance();
            UrlHandler urlHandler = new UrlHandler(settings.getCities()[settings.getCurrentIndexOfCity()]);
            DataHandler dataHandler = new DataHandler(urlHandler.getData());
            currDoubleTemp = dataHandler.getWeatherData().getMain().getTemp() - 273.15f;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public double getCurrDoubleTemp() {
        calcTemp();
        return currDoubleTemp;
    }

    class WeatherServiceBinder extends Binder {
        BoundWeatherService getService() {
            return BoundWeatherService.this;
        }

        double getCurrDoubleTemp() {
            return getService().getCurrDoubleTemp();
        }
    }
}
