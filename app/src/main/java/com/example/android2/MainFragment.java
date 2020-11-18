package com.example.android2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android2.weather.WeatherData;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment implements View.OnClickListener {
    public static final String LOG = "MyLog";

    Settings settings;

    MaterialTextView textViewOfCity, tv2;
    RecyclerView rwTemperature;
    ThermometerView thermometerView;
    ImageView imageWeather;

    OpenWeather openWeather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewOfCity = view.findViewById(R.id.textViewCity);
        imageWeather = view.findViewById(R.id.imageWeather);

        (view.findViewById(R.id.button)).setOnClickListener(this);
        (view.findViewById(R.id.buttonInfo)).setOnClickListener(this);

        tv2 = view.findViewById(R.id.textView2);
        thermometerView = view.findViewById(R.id.thermo);

        initRetrofit();
    }

    private void initRecyclerView(int[] data) {
        rwTemperature.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rwTemperature.setLayoutManager(layoutManager);

        TemperatureAdapter adapter = new TemperatureAdapter(data);
        rwTemperature.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        settings = Settings.getInstance();
        textViewOfCity.setText(settings.getCities()[settings.getCurrentIndexOfCity()]);

        //initRecyclerView(settings.getTemperatures()[settings.getCurrentIndexOfCity()]);

        View v = requireView().getRootView().findViewById(R.id.nav_host_fragment);
        v.setBackgroundResource(settings.isDarkThemeFlag() ? R.drawable.dark : R.drawable.background);

        tempUpdate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button :
                CitySelectionFragment citySelectionFragment = new CitySelectionFragment();
                citySelectionFragment.setDialogCityListener(dialogCityListener);
                citySelectionFragment.show(requireActivity().getSupportFragmentManager(), null);
                break;
            case R.id.buttonInfo :
                Snackbar.make(requireView(), "Получить информацию о городе?", Snackbar.LENGTH_LONG)
                        .setAction("OK", v1 -> {
                            String url = "https://en.wikipedia.org/wiki/" + settings.getCities()[settings.getCurrentIndexOfCity()];
                            Uri uri = Uri.parse(url);
                            Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(browser);
                        }).show();
                break;
        }
    }

    private final OnDialogCityListener dialogCityListener = new OnDialogCityListener() {
        @Override
        public void onDialogCity() {
            textViewOfCity.setText(settings.getCities()[settings.getCurrentIndexOfCity()]);

            tempUpdate();
        }
    };

    private void tempUpdate() {
        String city = settings.getCities()[settings.getCurrentIndexOfCity()];
        requestRetrofit(city);
    }

    private void requestRetrofit(String city) {
        openWeather.loadWeather(city, BuildConfig.WEATHER_API_KEY)
                .enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                        Log.i(LOG, "onResponse");
                        if (response.body() != null) {
                            double currDoubleTemp = response.body().getMain().getTemp() - 273.15f;
                            String currStringTemp = ((currDoubleTemp > 0) ? "+" : "") +
                                    String.format(Locale.US, "%.2f", currDoubleTemp) + "°";
                            tv2.setText(currStringTemp);
                            thermometerView.setValue((float) currDoubleTemp);
                            updateImageWeather(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherData> call, Throwable t) {
                        Log.i(LOG, "onFailure");
                    }
                });
    }

    private void updateImageWeather(WeatherData body) {
        Picasso.get()
                .load()
                .into(imageWeather);
    }

    private void initRetrofit() {
        Retrofit retrofit;
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeather = retrofit.create(OpenWeather.class);
    }
}