package com.example.android2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.android2.R;
import com.example.android2.fragment.SettingsFragment;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = initToolbar();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_second, new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.second_toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }
}