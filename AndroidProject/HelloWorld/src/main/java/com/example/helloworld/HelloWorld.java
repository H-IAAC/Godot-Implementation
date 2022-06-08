package com.example.helloworld;

import android.util.ArraySet;

import androidx.annotation.NonNull;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Mind;

public class HelloWorld extends GodotPlugin {

    private Mind mind;
    private TestCodelet testCodelet;

    public HelloWorld(Godot godot) {
        super(godot);

        mind = new Mind();
        mind.createCodeletGroup("Teste");

        testCodelet = new TestCodelet("t1", this);
        mind.insertCodelet(testCodelet, "Teste");

        mind.start();
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "HelloWorld";
    }

    @NonNull
    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList("hello", "sendSignal");
    }

    public String hello() {
        return "Hello World!";
    }

    public void sendSignal(String v) {
        emitSignal("hello", v);
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo("hello", String.class));

        return signals;
    }
}