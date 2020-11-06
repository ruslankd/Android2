package com.example.android2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

public class MainFragment extends Fragment implements View.OnClickListener {

    Settings settings;
    MaterialTextView textViewOfCity, tv2;
    RecyclerView rwTemperature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        textViewOfCity = root.findViewById(R.id.textViewCity);

        (root.findViewById(R.id.button)).setOnClickListener(this);
        (root.findViewById(R.id.buttonInfo)).setOnClickListener(this);

        tv2 = root.findViewById(R.id.textView2);
        rwTemperature = root.findViewById(R.id.rwTemperature);


        return root;
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

        tv2.setText(settings.getTemperature());

        //initRecyclerView(settings.getTemperatures()[settings.getCurrentIndexOfCity()]);

        View v = requireView().getRootView().findViewById(R.id.nav_host_fragment);
        v.setBackgroundResource(settings.isDarkThemeFlag() ? R.drawable.dark : R.drawable.background);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button :
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_city_selection);
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
}