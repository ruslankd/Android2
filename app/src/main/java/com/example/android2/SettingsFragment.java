package com.example.android2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {

    SwitchMaterial darkThemeSwitch;
    Settings settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_settings, container, false);

        darkThemeSwitch = layout.findViewById(R.id.switch1);
        settings = Settings.getInstance();

        layout.findViewById(R.id.switch1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setDarkTheme(darkThemeSwitch.isChecked());
                View view = getView().getRootView().findViewById(R.id.nav_host_fragment);
                view.setBackgroundResource(settings.isDarkThemeFlag() ? R.drawable.dark : R.drawable.background);
            }
        });

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        darkThemeSwitch.setChecked(settings.isDarkThemeFlag());
    }
}