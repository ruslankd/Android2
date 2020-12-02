package com.example.android2;

import com.example.android2.weather.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherData> loadWeather(@Query("q") String city, @Query("appid") String keyApi);

    @GET("data/2.5/weather")
    Call<WeatherData> loadWeatherByCoord(@Query("lat") String latitudeLine,
                                         @Query("lon") String longitudeLine,
                                         @Query("appid") String keyApi);
}
