package com.example.android2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;

public class MainFragment extends Fragment implements View.OnClickListener {

    Settings settings;
    MaterialTextView textViewOfCity, tv2;
    RecyclerView rwTemperature;
    ThermometerView thermometerView;

    private boolean isBound = false;
    private BoundWeatherService.WeatherServiceBinder boundWeatherService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewOfCity = view.findViewById(R.id.textViewCity);

        (view.findViewById(R.id.button)).setOnClickListener(this);
        (view.findViewById(R.id.buttonInfo)).setOnClickListener(this);

        tv2 = view.findViewById(R.id.textView2);
        thermometerView = view.findViewById(R.id.thermo);

        Intent intent = new Intent(requireActivity(), BoundWeatherService.class);
        requireActivity().bindService(intent, boundWeatherServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (isBound) {
            requireActivity().unbindService(boundWeatherServiceConnection);
        }
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
//        textViewOfCity.setText(settings.getCities()[settings.getCurrentIndexOfCity()]);

        //initRecyclerView(settings.getTemperatures()[settings.getCurrentIndexOfCity()]);

        View v = requireView().getRootView().findViewById(R.id.nav_host_fragment);
        v.setBackgroundResource(settings.isDarkThemeFlag() ? R.drawable.dark : R.drawable.background);
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
        if (boundWeatherService == null) {
            tv2.setText("");
        } else {
            final HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            final Handler handler = new Handler(handlerThread.getLooper());
            handler.post(() -> {
                double temp = boundWeatherService.getCurrDoubleTemp();
                String tempS = ((temp > 0) ? "+" : "") + String.format(Locale.US,"%.2f", temp) + "°";
                tv2.post(() -> {
                    thermometerView.setValue((float) temp);
                    tv2.setText(tempS);
                });
            });

        }
    }

    private final ServiceConnection boundWeatherServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            boundWeatherService = (BoundWeatherService.WeatherServiceBinder) service;
            isBound = boundWeatherService != null;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            boundWeatherService = null;
        }
    };
}