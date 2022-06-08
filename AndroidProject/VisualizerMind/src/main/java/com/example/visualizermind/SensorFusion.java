package com.example.visualizermind;

import android.location.Location;

import com.example.visualizermind.datacollection.DataCollector;
import com.example.visualizermind.util.Basis;
import com.example.visualizermind.util.DeviceState;
import com.example.visualizermind.util.RawDeviceState;
import com.example.visualizermind.util.Vector3;

public class SensorFusion {
    /*
        On every update called, will run through a sensor fusion routine that uses accelerometer,
        magnetometer, gyroscope and fused location data to obtain an estimate of the device's
        position and velocity. It follows the steps:

        1 - A low pass filter is used to separate linear acceleration and gravity vectors from
        accelerometer data
        2 - A mathematical model is used to calculate orientation from magnetometer and gravity
        data
        3 - Orientation is calculated once again, this once through dead reckoning with gyroscope
        data
        4 - The values obtained for orientation are fused
        5 - The orientation and the acceleration are used to calculate the position and velocity
        in a global frame of reference through dead reckoning
        6 - The position and velocity in a global frame of reference are calculated once again,
        this once through data from fused location
        7 - The values obtained for position and velocity in a global frame of reference are fused
        through the use of a kalman filter

        Through this method, we can obtain the best estimates for orientation, position and velocity

        The orientation will be given as v_x = East, v_y = North and v_z = Down
    */
    private static final float DISTANCE_PER_DEGREE = 111000;

    private final float ACCELEROMETER_VAR = 0.01f;
    private final float MAGNETOMETER_VAR = 0.01f;
    private final float GYROSCOPE_VAR = 0.01f;
    private final float LOCATION_VAR = 2f;

    private DataCollector dataCollector;
    private DeviceState deviceState;

    private Vector3 gravity;
    private Vector3 baseLocationPos;
    private Vector3 currentLocationPos;
    private Vector3 lastLocationPos;

    public SensorFusion(DataCollector dataCollector) {
        this.dataCollector = dataCollector;
        deviceState = new DeviceState(new Basis(), new Vector3(), new Vector3());

        gravity = new Vector3();
        baseLocationPos = null;
        currentLocationPos = new Vector3();
        lastLocationPos = new Vector3();
    }

    private static Vector3 transformCanonicalToOur(Vector3 v) {
        /*
            Transforms v, in the device's canonical basis, into the basis we are using
        */

        return new Vector3(v.y, -v.x, v.z);
    }

    public void reset() {
        deviceState = new DeviceState();
        baseLocationPos = null;
    }

    public void update(float deltaT) {
        /*
            Will run through the steps described above to update the values for the deviceState.
            From here, the steps will only be referred to by their number
        */

        RawDeviceState currentRawState = dataCollector.getData();

        Vector3 localMagnetometer = currentRawState.IMUData.magnetomer;
        Vector3 localAccelerometer = currentRawState.IMUData.accelerometer;
        Vector3 localGyroscope = currentRawState.IMUData.gyroscope;

        //Vector3 localMagnetometer = transformCanonicalToOur(currentRawState.IMUData.magnetomer);
        //Vector3 localAccelerometer = transformCanonicalToOur(currentRawState.IMUData.accelerometer);
        //Vector3 localGyroscope = transformCanonicalToOur(currentRawState.IMUData.gyroscope);

        /*
            STEP 1 - LOW PASS FILTER
        */

        float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        gravity = new Vector3(alpha * gravity.x + (1 - alpha) * localAccelerometer.x, alpha * gravity.y + (1 - alpha) * localAccelerometer.y, alpha * gravity.z + (1 - alpha) * localAccelerometer.z);

        // Remove the gravity contribution with the high-pass filter.
        Vector3 linearAcceleration = new Vector3(localAccelerometer.x - gravity.x, localAccelerometer.y - gravity.y, localAccelerometer.z - gravity.z);

        /*
            STEP 2 - ORIENTATION CALCULATED THROUGH GRAVITY AND MAGNETOMETER

            Uses a similar method to the one described in https://youtu.be/0rlvvYgmTvI?t=160 but we
            use up as the positive orientation for y
        */

        Vector3 magnetometerGravityOrientationY = (gravity).normalized();
        Vector3 magnetometerGravityOrientationX = (magnetometerGravityOrientationY.cross(localMagnetometer)).normalized();
        Vector3 magnetometerGravityOrientationZ = (magnetometerGravityOrientationX.cross(magnetometerGravityOrientationY)).normalized();

        Basis magnetometerGravityBasis = (new Basis(magnetometerGravityOrientationX, magnetometerGravityOrientationY, magnetometerGravityOrientationZ)).inverted();

        /*
            STEP 3 - ORIENTATION CALCULATED THROUGH GYROSCOPE

            Uses a quaternion generated from gyroscope data to rotate the current
            basis. Based on
            https://stackoverflow.com/questions/39441900/how-use-raw-gryoscope-data-s-for-calculating-3d-rotation
        */

        Basis gyroscopeOrientation = deviceState.basis.rotateQuat(currentRawState.IMUData.gyroscope.x * deltaT * 0.5f, currentRawState.IMUData.gyroscope.x * deltaT * 0.5f, currentRawState.IMUData.gyroscope.x * deltaT * 0.5f, 1);
        gyroscopeOrientation = gyroscopeOrientation.orthonormalized();

        // TEMP
        gyroscopeOrientation = magnetometerGravityBasis;

        /*
            STEP 4 - FUSION OF ORIENTATION DATA
        */

        Basis orientation = new Basis(new Vector3(0, 0, 0), new Vector3(0, 0, 0), new Vector3(0, 0, 0));
        float[] aux;

        final double mergeVar = Math.pow(MAGNETOMETER_VAR, 2) + Math.pow(ACCELEROMETER_VAR, 2);

        aux = combine(magnetometerGravityBasis.v_x.x, (float) Math.pow(mergeVar, 0.5), gyroscopeOrientation.v_x.x, deltaT * GYROSCOPE_VAR);
        orientation.v_x.x = aux[0];
        aux = combine(magnetometerGravityBasis.v_x.y, (float) Math.pow(mergeVar, 0.5), gyroscopeOrientation.v_x.y, deltaT * GYROSCOPE_VAR);
        orientation.v_x.y = aux[0];
        aux = combine(magnetometerGravityBasis.v_x.z, (float) Math.pow(mergeVar, 0.5), gyroscopeOrientation.v_x.z, deltaT * GYROSCOPE_VAR);
        orientation.v_x.z = aux[0];

        aux = combine(magnetometerGravityBasis.v_y.x, (float) Math.pow(mergeVar, 0.5), gyroscopeOrientation.v_y.x, deltaT * GYROSCOPE_VAR);
        orientation.v_y.x = aux[0];
        aux = combine(magnetometerGravityBasis.v_y.y, (float) Math.pow(mergeVar, 0.5), gyroscopeOrientation.v_y.y, deltaT * GYROSCOPE_VAR);
        orientation.v_y.y = aux[0];
        aux = combine(magnetometerGravityBasis.v_y.z, (float) Math.pow(mergeVar, 0.5), gyroscopeOrientation.v_y.z, deltaT * GYROSCOPE_VAR);
        orientation.v_y.z = aux[0];

        aux = combine(magnetometerGravityBasis.v_z.x, (float) Math.pow(mergeVar, 0.5), gyroscopeOrientation.v_z.x, deltaT * GYROSCOPE_VAR);
        orientation.v_z.x = aux[0];
        aux = combine(magnetometerGravityBasis.v_z.y, (float) Math.pow(mergeVar, 0.5), gyroscopeOrientation.v_z.y, deltaT * GYROSCOPE_VAR);
        orientation.v_z.y = aux[0];
        aux = combine(magnetometerGravityBasis.v_z.z, (float) Math.pow(mergeVar, 0.5), gyroscopeOrientation.v_z.z, deltaT * GYROSCOPE_VAR);
        orientation.v_z.z = aux[0];

        /*
            STEP 5 - POSITION AND VELOCITY CALCULATED THROUGH ORIENTATION AND LINEAR ACCELERATION
        */
        Vector3 globalAcceleration = orientation.fromFrameToGlobal(linearAcceleration);

        Vector3 deltaPos = globalAcceleration.multiply((float) (Math.pow(deltaT, 2)/2));
        Vector3 linearAccelerationPosition = (deviceState.position.add(deviceState.velocity.multiply(deltaT))).add(deltaPos);

        Vector3 linearAccelerationVelocity = deviceState.velocity.add(globalAcceleration.multiply(deltaT));

        VisualizerMind.getInstance().updateValue(orientation.fromFrameToGlobal(globalAcceleration).toString(), "ACCELERATION");

        /*
            STEP 6 - POSITION AND VELOCITY CALCULATED THROUGH FUSED LOCATION
        */

        Vector3 fusedLocationPosition;
        float locationSpeed;
        if (currentRawState.location == null) {
            fusedLocationPosition = new Vector3(0, 0, 0);
            locationSpeed = 0;
        } else {
            if (baseLocationPos == null) {
                baseLocationPos = posFromLocation(currentRawState.location);
            }
            fusedLocationPosition = posFromLocation(currentRawState.location).sub(baseLocationPos);
            locationSpeed = currentRawState.location.getSpeed();
        }

        if (!currentLocationPos.equals(fusedLocationPosition)) {
            lastLocationPos = currentLocationPos.copy();
        }
        currentLocationPos = fusedLocationPosition;

        Vector3 fusedLocationVelocity = ((currentLocationPos.sub(lastLocationPos)).normalized()).multiply(locationSpeed);

        /*
            STEP 7 - FUSION OF POSITION AND VELOCITY DATA
        */

        Vector3 position = new Vector3();

        aux = combine(fusedLocationPosition.x, LOCATION_VAR, linearAccelerationPosition.x, deltaT * ACCELEROMETER_VAR / ((float) Math.pow(2, 0.5)));
        position.x = aux[0];
        aux = combine(fusedLocationPosition.y, LOCATION_VAR, linearAccelerationPosition.y, deltaT * ACCELEROMETER_VAR / ((float) Math.pow(2, 0.5)));
        position.y = aux[0];
        aux = combine(fusedLocationPosition.z, LOCATION_VAR, linearAccelerationPosition.z, deltaT * ACCELEROMETER_VAR / ((float) Math.pow(2, 0.5)));
        position.z = aux[0];

        Vector3 velocity = new Vector3();

        aux = combine(fusedLocationVelocity.x, LOCATION_VAR/deltaT, linearAccelerationVelocity.x, ACCELEROMETER_VAR * deltaT);
        velocity.x = aux[0];
        aux = combine(fusedLocationVelocity.y, LOCATION_VAR/deltaT, linearAccelerationVelocity.y, ACCELEROMETER_VAR * deltaT);
        velocity.y = aux[0];
        aux = combine(fusedLocationVelocity.z, LOCATION_VAR/deltaT, linearAccelerationVelocity.z, ACCELEROMETER_VAR * deltaT);
        velocity.z = aux[0];

        /*
            UPDATE DEVICE STATE
        */

        VisualizerMind.getInstance().updateValue(currentRawState.IMUData.magnetomer.toString(), "M");

        //Vector3 c = orientation.v_x;
        //orientation.v_x = orientation.v_y.multiply(-1);
        //orientation.v_y = c;

        deviceState.position = position;
        deviceState.velocity = velocity;
        deviceState.basis = orientation;
    }

    public DeviceState getData() {
        return deviceState;
    }

    private float[] combine(float m1, float v1, float m2, float v2) {
        /*
            Given the mean and variance of two normal distribution, returns the
            mean and variance of their combination
        */

        float k = (float) (Math.pow(v1, 2)/(Math.pow(v1, 2) + Math.pow(v2, 2)));
        float nm = m1 + k * (m2 - m1);
        float nv = (float) (Math.pow(Math.pow(v1, 2) * (1 - k), 0.5));

        float[] r = {nm, nv};
        return r;
    }

    private static Vector3 posFromLocation(Location location) {
        /*
            Returns a Vector3 that represents an absolute position from latitude and longitude
        */

        return new Vector3((float) -location.getLongitude() * DISTANCE_PER_DEGREE, (float) location.getAltitude(), (float) location.getLatitude() * DISTANCE_PER_DEGREE);
    }

    /*

    // Unused functions for Kalman Filter. Might be useful later

    private float[] predict(float var1, float mean1, float var2, float mean2) {
        float[] r = {var1 + var2, mean1 + mean2};
        return r;
    }

    private float[] correct(float var1, float mean1, float var2, float mean2) {
        if (var1 == 0) {
            var1 = 0.01f;
        }
        if (var2 == 0) {
            var2 = 0.01f;
        }
        float[] r = {(var1 * mean2 + var2 * mean1)/(var1 + var2), 1/(1/var1 + 1/var2)};
        return r;
    }

    private float[] combine(float var, float mean, float predictedVar, float predictedMean, float measuredVar, float measuredMean) {
        float[] aux = predict(var, mean, predictedVar, predictedMean);
        return correct(aux[0], aux[1], measuredVar, measuredMean);
    }
    */
}
