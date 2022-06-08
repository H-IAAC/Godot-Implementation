package com.example.visualizermind.util;

import android.location.Location;

public class RawDeviceState {
    public IMUDataPack IMUData;
    public Location location;

    public RawDeviceState(IMUDataPack IMUData, Location location) {
        this.IMUData = IMUData;
        this.location = location;
    }

    @Override
    public String toString() {
        String locationText;
        if (location == null) {
            locationText = "LATITUDE: 0; LONGITUDE 0; ";
        } else {
            locationText = "LATITUDE: " + String.valueOf(location.getLatitude()) + "; LONGITUDE: " + String.valueOf(location.getLongitude()) + "; ";
        }

        return locationText + IMUData.toString();
    }
}
