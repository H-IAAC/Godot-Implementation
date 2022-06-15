package com.example.godotrl.util;

/*
    Used to control information update timing

    -> Awaiting for sensor data -> Awaiting for car update -> Awaiting for car detection -> Awaiting for close cars update
    -> Awaiting for state update -> Awaiting for learner update -> Awaiting for motor data read ->
*/
public class Updater {
    UpdateState state = UpdateState.SENSOR;

    public boolean updateSensor() {
        if (state == UpdateState.SENSOR) {
            state = UpdateState.CAR_UPDATE;
            return true;
        }
        return false;
    }

    public boolean updateCarUpdate() {
        if (state == UpdateState.CAR_UPDATE) {
            state = UpdateState.CAR_READ;
            return true;
        }
        return false;
    }

    public boolean updateCarRead() {
        if (state == UpdateState.CAR_READ) {
            state = UpdateState.CLOSE_CAR;
            return true;
        }
        return false;
    }

    public boolean updateCloseCar() {
        if (state == UpdateState.CLOSE_CAR) {
            state = UpdateState.STATE_UPDATE;
            return true;
        }
        return false;
    }

    public boolean updateState() {
        if (state == UpdateState.STATE_UPDATE) {
            state = UpdateState.LEARNER;
            return true;
        }
        return false;
    }

    public boolean updateLearner() {
        if (state == UpdateState.LEARNER) {
            state = UpdateState.MOTOR;
            return true;
        }
        return false;
    }

    public boolean updateMotor() {
        if (state == UpdateState.MOTOR) {
            state = UpdateState.SENSOR;
            return true;
        }
        return false;
    }
}
