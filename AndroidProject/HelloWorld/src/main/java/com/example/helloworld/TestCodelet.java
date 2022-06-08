package com.example.helloworld;

import junit.framework.Test;

import br.unicamp.cst.core.entities.Codelet;

public class TestCodelet extends Codelet {
    private int emotion;
    private float emotionModifier;
    private HelloWorld plugin;

    public TestCodelet(String name, HelloWorld plugin) {
        setName(name);

        this.plugin = plugin;

        emotion = 0;
        emotionModifier = 1;
    }

    @Override
    public void accessMemoryObjects() {

    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        emotion += emotionModifier;
        if (emotion >= 100) {
            emotionModifier = -1;
        } else if (emotion <= 0) {
            emotionModifier = 1;
        }

        plugin.sendSignal(String.valueOf(emotion));
    }
}
