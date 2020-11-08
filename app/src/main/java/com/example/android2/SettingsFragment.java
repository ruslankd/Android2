package com.example.android2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
                View view = getView().getRootView().findViewById(R.id.nav_host_fragment_second);
                view.setBackgroundResource(settings.isDarkThemeFlag() ? R.drawable.dark : R.drawable.background);
            }
        });

        Toolbar toolbar = getActivity().findViewById(R.id.second_toolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.ic_baseline_arrow_back_24, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        darkThemeSwitch.setChecked(settings.isDarkThemeFlag());
    }

    @Override
    public void onResume() {
        super.onResume();
        View v = requireView().getRootView().findViewById(R.id.nav_host_fragment_second);
        v.setBackgroundResource(settings.isDarkThemeFlag() ? R.drawable.dark : R.drawable.background);
    }
}