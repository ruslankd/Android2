package com.example.android2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class CitySelectionFragment extends BottomSheetDialogFragment {

    RecyclerView rvCities;
    Settings settings;
    View root;

    private OnDialogCityListener dialogCityListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settings = Settings.getInstance();
        String[] data = settings.getCities();
        root = view;
        initRwCities(data);
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
            dismiss();
            if (dialogCityListener != null) dialogCityListener.onDialogCity();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setDialogCityListener(OnDialogCityListener dialogCityListener) {
        this.dialogCityListener = dialogCityListener;
    }
}