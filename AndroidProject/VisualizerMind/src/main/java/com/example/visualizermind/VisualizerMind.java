package com.example.visualizermind;

import android.app.Activity;
import android.util.ArraySet;

import androidx.annotation.NonNull;

import com.example.visualizermind.datacollection.RealTimeDataCollector;
import com.example.visualizermind.util.DeviceState;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class VisualizerMind extends GodotPlugin {
    private static VisualizerMind instance = null;

    private DeviceState deviceState;
    private SensorFusion sensorFusion;

    public VisualizerMind(Godot godot) {
        super(godot);

        deviceState = new DeviceState();

        instance = this;
    }

    public static VisualizerMind getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "VisualizerMind";
    }

    @NonNull
    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList("startRealTime", "update", "getBasis", "getWorldPosition", "resetPosition");
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo("update_value", String.class, String.class));
        signals.add(new SignalInfo("create_notification", String.class));

        return signals;
    }

    public void startRealTime() {
        /*
            Starts the sensor fusion for gathering real time data
        */

        sensorFusion = new SensorFusion(new RealTimeDataCollector());
    }

    public void resetPosition() {
        sensorFusion.reset();
    }

    public void update(float deltaT) {
        /*
            Called by Godot every frame. Calls an update to the sensor fusion and updates data
        */

        // GET UPDATED DATA
        sensorFusion.update(deltaT);

        deviceState = sensorFusion.getData();

        // UPDATE NOTIFICATIONS
        emitSignal("update_value", deviceState.position.toString(), "POSITION");
        emitSignal("update_value", deviceState.velocity.toString(), "VELOCITY");
        emitSignal("update_value", deviceState.basis.toString(), "BASIS");
    }

    public String getWorldPosition() {
        return deviceState.position.x + ";" + deviceState.position.y + ";" + deviceState.position.z;
    }

    public String getBasis() {
        return deviceState.basis.v_x.x + ";" + deviceState.basis.v_x.y + ";" + deviceState.basis.v_x.z + ";" + deviceState.basis.v_y.x + ";" + deviceState.basis.v_y.y + ";" + deviceState.basis.v_y.z + ";" + deviceState.basis.v_z.x + ";" + deviceState.basis.v_z.y + ";" + deviceState.basis.v_z.z;
    }

    public Activity getActivityOutside() {
        return getActivity();
    }

    public void createNotification(String notif) {
        emitSignal("create_notification", notif);
    }

    public void updateValue(String value, String code) {
        emitSignal("update_value", value, code);
    }
}
