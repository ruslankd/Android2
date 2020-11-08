package com.example.android2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

public class CitySelectionFragment extends Fragment {

    RecyclerView rvCities;
    Settings settings;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_city_selection, container, false);

        settings = Settings.getInstance();
        String[] data = settings.getCities();
        initRwCities(data);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getResources().getString(R.string.city_selection));

        return root;
    }

    private void initRwCities(String[] data) {
        rvCities = root.findViewById(R.id.rvCities);
        rvCities.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.separator, null)));
        rvCities.addItemDecoration(itemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCities.setLayoutManager(layoutManager);

        CityAdapter adapter = new CityAdapter(data);
        rvCities.setAdapter(adapter);

        adapter.SetOnItemClickListener((v, position) -> {
            settings.setCurrentIndexOfCity(position);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new MainFragment())
                    .commit();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}