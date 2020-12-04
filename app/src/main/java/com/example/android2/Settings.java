package com.example.android2;

public class Settings {
    private static Settings instance = null;

    private Settings() {}

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }

        return instance;
    }

    private boolean darkTheme = false;

    private final String[] cities = new String[]{
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

    private double latitude = 0f;
    private double longitude = 0f;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
