package com.example.android2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class SettingsFragment extends Fragment {

    Settings settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settings = Settings.getInstance();

        Toolbar toolbar = getActivity().findViewById(R.id.second_toolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.ic_baseline_arrow_back_24, null));
        toolbar.setTitle(R.string.settings);
        toolbar.setNavigationOnClickListener(v -> getActivity().finish());

        TextView tvTheme = view.findViewById(R.id.tvTheme);
        tvTheme.setOnClickListener(view1 -> {
            final String[] items = getResources().getStringArray(R.array.theme_array);
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.select_theme)
                    .setSingleChoiceItems(items, settings.isDarkThemeFlag() ? 1 : 0, (dialogInterface, i) -> settings.setDarkTheme(i == 1))
                    .setPositiveButton("Ok", (dialogInterface, i) -> {
                        View v = getView().getRootView().findViewById(R.id.nav_host_fragment_second);
                        v.setBackgroundResource(settings.isDarkThemeFlag() ? R.drawable.dark : R.drawable.background);
                    })
                    .create()
                    .show();
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        View v = requireView().getRootView().findViewById(R.id.nav_host_fragment_second);
        v.setBackgroundResource(settings.isDarkThemeFlag() ? R.drawable.dark : R.drawable.background);
    }
}