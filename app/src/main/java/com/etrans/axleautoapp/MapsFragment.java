package com.etrans.axleautoapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MapsFragment extends Fragment {

    private static final String TAG = "MapsFragment";
    private WebView mapWebView;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mapWebView = rootView.findViewById(R.id.mapWebView);
        WebView.setWebContentsDebuggingEnabled(true);

        // Set WebViewClient to handle page navigation
        mapWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "Page loaded: " + url);
            }
        });

        mapWebView.setWebChromeClient(new WebChromeClient());

        WebSettings webSettings = mapWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        mapWebView.loadUrl("https://maps.app.goo.gl/F6rgHFXHjpUPtFPc9");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        enableMyLocation();

        return rootView;
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            updateLocationOnMap(location);
                        } else {
                            Log.e(TAG, "Location is null");
                        }
                    })
                    .addOnFailureListener(e -> Log.e(TAG, "Failed to get location", e));
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void updateLocationOnMap(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.d(TAG, "Updating location on map: " + latitude + ", " + longitude);
        mapWebView.loadUrl("javascript:updateLocation(" + latitude + "," + longitude + ")");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Log.e(TAG, "Location permission denied");
            }
        }
    }
}
