package com.example.visualizermind.datacollection.modules;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.visualizermind.VisualizerMind;
import com.example.visualizermind.util.IMUDataPack;
import com.example.visualizermind.util.Vector3;

public class IMUCollectionModule {
    /*
        Will constantly update with IMU data, which can be gathered as a IMUDataPack via
        getData(). Vectors will have 0s in positions which haven't been updated yet
    */

    private final int SAMPLING_PERIOD = 1;

    private IMUDataPack IMUData;
    private SensorManager sensorManager;

    public IMUCollectionModule() {
        IMUData = new IMUDataPack(new Vector3(0, 0, 0), new Vector3(0, 0, 0), new Vector3(0, 0, 0));

        initializeIMU();
    }

    private void initializeIMU() {
        sensorManager = (SensorManager) VisualizerMind.getInstance().getActivityOutside().getSystemService(Context.SENSOR_SERVICE);

        initializeAccelerometer();
        initializeMagnetometer();
        initializeGyroscope();
    }

    private void initializeAccelerometer() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                IMUData.accelerometer = new Vector3(event.values[0], event.values[1], event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorEventListener, sensor, SAMPLING_PERIOD);
    }

    private void initializeMagnetometer() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                IMUData.magnetomer = new Vector3(event.values[0], event.values[1], event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorEventListener, sensor, SAMPLING_PERIOD);
    }

    private void initializeGyroscope() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                IMUData.gyroscope = new Vector3(event.values[0], event.values[1], event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorEventListener, sensor, SAMPLING_PERIOD);
    }

    public IMUDataPack getData() {
        return IMUData;
    }
}
