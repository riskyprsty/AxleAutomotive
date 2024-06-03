package com.etrans.axleautoapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Button;
import com.etrans.axleautoapp.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String param1;
    private String param2;

    private GridLayout gridLayout;
    private Button toggleButton;
    private final List<Integer> thirdColumnViewIds = new ArrayList<>();

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        gridLayout = view.findViewById(R.id.gRight);
        toggleButton = view.findViewById(R.id.showToggle);

        thirdColumnViewIds.add(R.id.r_id_voltage);
        thirdColumnViewIds.add(R.id.r_id_rpm);
        thirdColumnViewIds.add(R.id.r_id_current);
        thirdColumnViewIds.add(R.id.r_id_faultCode);
        thirdColumnViewIds.add(R.id.r_id_cTemp);
        thirdColumnViewIds.add(R.id.r_id_eTemp);
        thirdColumnViewIds.add(R.id.r_id_tCoef);
        thirdColumnViewIds.add(R.id.r_id_gearStatus);
        thirdColumnViewIds.add(R.id.r_id_controllerStatus);
        thirdColumnViewIds.add(R.id.l_id_voltage);
        thirdColumnViewIds.add(R.id.l_id_rpm);
        thirdColumnViewIds.add(R.id.l_id_current);
        thirdColumnViewIds.add(R.id.l_id_faultCode);
        thirdColumnViewIds.add(R.id.l_id_cTemp);
        thirdColumnViewIds.add(R.id.l_id_eTemp);
        thirdColumnViewIds.add(R.id.l_id_tCoef);
        thirdColumnViewIds.add(R.id.l_id_gearStatus);
        thirdColumnViewIds.add(R.id.l_id_controllerStatus);

        for (Integer viewId : thirdColumnViewIds) {
            View childView = view.findViewById(viewId);
            if (childView != null) {
                childView.setVisibility(View.GONE);
            }
        }

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility();
            }
        });

        return view;
    }

    private void toggleVisibility() {
        int newVisibility = (thirdColumnVisible()) ? View.GONE : View.VISIBLE;

        for (int viewId : thirdColumnViewIds) {
            View childView = getView().findViewById(viewId);
            if (childView != null) {
                childView.setVisibility(newVisibility);
            }
        }

        String buttonText = (newVisibility == View.VISIBLE) ? "Hide" : "Show";
        toggleButton.setText(buttonText);
    }

    private boolean thirdColumnVisible() {
        return getView() != null &&
                getView().findViewById(thirdColumnViewIds.get(0)).getVisibility() == View.VISIBLE;
    }
}