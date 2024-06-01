package com.etrans.axleautoapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;
import com.etrans.axleautoapp.SpeedometerView; // Import SpeedometerView

public class HomeFragment extends Fragment {
    SpeedometerView speed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize SpeedometerView
        speed = view.findViewById(R.id.speedometer);
        speed.setLabelConverter(new SpeedometerView.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });

        // Configure value range and ticks
        speed.setMaxSpeed(100);
        speed.setMajorTickStep(25);
        speed.setMinorTicks(0);

        // Configure value range colors
        speed.addColoredRange(0, 50, Color.GREEN);
        speed.addColoredRange(50, 75, Color.YELLOW);
        speed.addColoredRange(75, 100, Color.RED);
        speed.setSpeed(75, 5000, 500);

        return view;
    }
}