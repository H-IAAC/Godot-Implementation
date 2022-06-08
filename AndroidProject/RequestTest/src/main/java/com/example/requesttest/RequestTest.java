package com.example.requesttest;

import android.util.ArraySet;

import androidx.annotation.NonNull;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RequestTest extends GodotPlugin {
    private String testValue = "Initial value";
    private String externalTestValue = "Not yet defined";

    public RequestTest(Godot godot) {
        super(godot);
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "RequestTest";
    }

    @NonNull
    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList("updateTestValue", "emitTestValue", "updateExternalTestValue", "printExternalTestValue", "endPrintExternalTestValue");
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo("print_test_value", String.class));
        signals.add(new SignalInfo("request_external_test_value"));

        return signals;
    }

    public void updateExternalTestValue(String newExternalTestValue) {
        externalTestValue = newExternalTestValue;
    }

    public void printExternalTestValue() {
        emitSignal("request_external_test_value");
    }

    public void endPrintExternalTestValue() {
        emitSignal("print_test_value", externalTestValue);
    }

    public void updateTestValue(String newTestValue) {
        testValue = newTestValue;
    }

    public void emitTestValue() {
        emitSignal("print_test_value", testValue);
    }
}
