package com.example.android2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android2.weather.Main;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.net.MalformedURLException;

public class MainFragment extends Fragment implements View.OnClickListener {

    Settings settings;
    MaterialTextView textViewOfCity, tv2;
    RecyclerView rwTemperature;

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

        try {
            tv2.setText(settings.getTemperature());
        } catch (MalformedURLException e) {
            ((MainActivity)getActivity()).errorDialog(e.getMessage());
        }

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
                citySelectionFragment.show(getActivity().getSupportFragmentManager(), null);
                break;
            case R.id.buttonInfo :
                Snackbar.make(requireView(), "Получить информацию о городе?", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = "https://en.wikipedia.org/wiki/" + settings.getCities()[settings.getCurrentIndexOfCity()];
                                Uri uri = Uri.parse(url);
                                Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(browser);
                            }
                        }).show();

                break;
        }
    }

    private OnDialogCityListener dialogCityListener = new OnDialogCityListener() {
        @Override
        public void onDialogCity() {
            textViewOfCity.setText(settings.getCities()[settings.getCurrentIndexOfCity()]);

            try {
                tv2.setText(settings.getTemperature());
            } catch (MalformedURLException e) {
                ((MainActivity)getActivity()).errorDialog(e.getMessage());
            }
        }
    };
}