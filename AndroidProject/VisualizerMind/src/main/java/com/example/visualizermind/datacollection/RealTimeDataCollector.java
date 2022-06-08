package com.example.visualizermind.datacollection;

import com.example.visualizermind.datacollection.modules.FusedLocationCollectionModule;
import com.example.visualizermind.datacollection.modules.IMUCollectionModule;
import com.example.visualizermind.util.RawDeviceState;

public class RealTimeDataCollector extends DataCollector {
    /*
        Collects data in real-time through the classes FusedLocationCollectionModule and
        IMUCollectionModule
    */

    private FusedLocationCollectionModule locationModule;
    private IMUCollectionModule IMUModule;

    public RealTimeDataCollector() {
        locationModule = new FusedLocationCollectionModule();
        IMUModule = new IMUCollectionModule();
    }

    @Override
    public RawDeviceState getData() {
        return new RawDeviceState(IMUModule.getData(), locationModule.getData());
    }
}
