package com.example.visualizermind.util;

import com.example.visualizermind.util.Vector3;

public class IMUDataPack {
    public Vector3 accelerometer;
    public Vector3 magnetomer;
    public Vector3 gyroscope;

    public IMUDataPack(Vector3 accelerometer, Vector3 magnetomer, Vector3 gyroscope) {
        this.accelerometer = accelerometer;
        this.magnetomer = magnetomer;
        this.gyroscope = gyroscope;
    }

    @Override
    public String toString() {
        return "ACCELEROMETER: " + accelerometer + "; MAGNETOMETER: " + magnetomer + "; GYROSCOPE: " + gyroscope;
    }
}
