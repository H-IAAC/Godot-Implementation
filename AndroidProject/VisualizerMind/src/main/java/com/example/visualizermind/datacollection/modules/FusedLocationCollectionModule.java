package com.example.visualizermind.datacollection.modules;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.visualizermind.VisualizerMind;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class FusedLocationCollectionModule {
    /*
        Will constantly update with fused location data, which can be gathered via getData().
        May return null if no location data has been gathered yet
    */

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;

    public FusedLocationCollectionModule() {
        // Asks for permission to access location, if it wasn't granted before
        if (ActivityCompat.checkSelfPermission(VisualizerMind.getInstance().getActivityOutside(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(VisualizerMind.getInstance().getActivityOutside(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VisualizerMind.getInstance().getActivityOutside(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        //Initializes the FusedLocationProvider
        initializeFusedLocation();
    }

    private void initializeFusedLocation() {
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                location = locationResult.getLastLocation();
            }
        };
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(100);
        locationRequest.setWaitForAccurateLocation(true);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(VisualizerMind.getInstance().getActivityOutside());
        if (ActivityCompat.checkSelfPermission(VisualizerMind.getInstance().getActivityOutside(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(VisualizerMind.getInstance().getActivityOutside(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, VisualizerMind.getInstance().getActivityOutside().getMainLooper());
    }

    public Location getData() {
        return location;
    }
}
