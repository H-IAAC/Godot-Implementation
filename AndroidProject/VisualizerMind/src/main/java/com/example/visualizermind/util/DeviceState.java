package com.example.visualizermind.util;

import com.example.visualizermind.util.Basis;
import com.example.visualizermind.util.Vector3;

public class DeviceState {
    public Basis basis;
    public Vector3 position;
    public Vector3 velocity;

    public DeviceState() {
        basis = new Basis();
        position = new Vector3();
        velocity = new Vector3();
    }

    public DeviceState(Basis basis, Vector3 position, Vector3 velocity) {
        this.basis = basis;
        this.position = position;
        this.velocity = velocity;
    }
}
