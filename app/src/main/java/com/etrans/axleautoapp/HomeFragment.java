package com.etrans.axleautoapp;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    SpeedometerView speed;
    private TextView textViewPsi;
    private TextView textViewCel;
    private TextView textViewPsi2;
    private TextView textViewCel2;
    private TextView tiltTextView;
    private TextView tiltTextView2;

    private ImageView wheelImageView;
    private ImageView wheelImageView2;

    private int psiValue = 0;
    private int celValue = 0;
    private Timer timer;
    private float currentRotation = 0f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Suhu dan Tekanan
        textViewPsi = view.findViewById(R.id.textViewPsi);
        textViewCel = view.findViewById(R.id.textViewCel);
        textViewPsi2 = view.findViewById(R.id.textViewPsi2);
        textViewCel2 = view.findViewById(R.id.textViewCel2);

        startUpdatingTextViews();

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

        tiltTextView = view.findViewById(R.id.tiltTextView);
        wheelImageView = view.findViewById(R.id.wheelImageView);
        tiltTextView2 = view.findViewById(R.id.tiltTextView2);
        wheelImageView2 = view.findViewById(R.id.wheelImageView2);

        Animation rotateAnimation1 = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate);
        Animation rotateAnimation2 = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate);

        wheelImageView.startAnimation(rotateAnimation1);
        wheelImageView2.startAnimation(rotateAnimation2);

        // Mengaitkan listener ke animasi putaran roda
        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Dapat diabaikan
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Dapat diabaikan
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Mendapatkan derajat rotasi pada setiap kali animasi diulang
                currentRotation = (currentRotation + 10) % 360; // Simulasikan rotasi roda
                updateTiltText((int) currentRotation);
            }
        };

        rotateAnimation1.setAnimationListener(animationListener);
        rotateAnimation2.setAnimationListener(animationListener);

        return view;
    }

    private void updateTiltText(int derajatKemiringan) {
        String tiltText = derajatKemiringan + "°";
        tiltTextView.setText(tiltText);
        tiltTextView2.setText(tiltText);
    }

    private void startUpdatingTextViews() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                psiValue++;
                celValue = (int) sigmoid(celValue);

                // limit ke 100
                if (psiValue >= 100) {
                    cancel();
                }

                // berhenti kalau sudah 37
                if (celValue >= 37) {
                    cancel();
                }

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewPsi.setText(psiValue + " Psi");
                        textViewCel.setText((int) celValue + "° C");
                        textViewPsi2.setText(psiValue + " Psi");
                        textViewCel2.setText((int) celValue + "° C");
                    }
                });
            }
        }, 0, 1000); // update tiap 300 ms
    }

    private double sigmoid(double x) {
        return 37 / (1 + Math.exp(-0.2 * x)); // pakek factor -0.2 sigmoid, ini simulasi untuk suhu
    }

    public void onDestroyView() {
        super.onDestroyView();
        // stop timer kalau fragment ditutup
        if (timer != null) {
            timer.cancel();
        }
    }
}
