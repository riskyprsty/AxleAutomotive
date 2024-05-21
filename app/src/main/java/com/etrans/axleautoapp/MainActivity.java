package com.etrans.axleautoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.graphics.Color;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MainActivity extends AppCompatActivity {
    SpeedometerView Speed;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SpeedometerView
        Speed = findViewById(R.id.speedometer);
        Speed.setLabelConverter(new SpeedometerView.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });

        // Configure value range and ticks
        Speed.setMaxSpeed(100);
        Speed.setMajorTickStep(25);
        Speed.setMinorTicks(0);

        // Configure value range colors
        Speed.addColoredRange(0, 50, Color.GREEN);
        Speed.addColoredRange(50, 75, Color.YELLOW);
        Speed.addColoredRange(75, 100, Color.RED);
        Speed.setSpeed(75, 5000, 500);

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.navigation_settings:
                    loadFragment(new SettingsFragment());
                    return true;
                case R.id.navigation_profile:
                    loadFragment(new ProfileFragment());
                    return true;
                case R.id.navigation_message:
                    loadFragment(new MessageFragment());
                    return true;
            }
            return false;
        });

        // Load the default fragment
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home); // change to whichever id should be default
        }
    }

    private void loadFragment(Fragment fragment) {
        // Load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
