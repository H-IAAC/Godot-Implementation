package com.example.godotwebapidemo.util;

public class Updater {
    State state = State.SENSOR;

    public boolean updateSensor() {
        if (state == State.SENSOR) {
            state = State.MOTOR;

            return true;
        }
        return false;
    }

    public boolean updateMotor() {
        if (state == State.MOTOR) {
            state = State.SENSOR;

            return true;
        }
        return false;
    }
}
