package com.etrans.axleautoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {
SpeedometerView Speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Speed = (SpeedometerView)findViewById(R.id.speedometer);
        Speed.setLabelConverter(new SpeedometerView.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }

        });

// configure value range and ticks
        Speed.setMaxSpeed(100);
        Speed.setMajorTickStep(25);
        Speed.setMinorTicks(0);

// Configure value range colors
        Speed.addColoredRange(0, 50, Color.GREEN);
        Speed.addColoredRange(50, 75, Color.YELLOW);
        Speed.addColoredRange(75, 100, Color.RED);
        Speed.setSpeed(25, 2000, 500);
    }

}