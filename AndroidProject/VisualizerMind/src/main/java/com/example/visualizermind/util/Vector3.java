package com.example.visualizermind.util;

import android.icu.text.DecimalFormat;

public class Vector3 {
    DecimalFormat df = new DecimalFormat();

    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        df.setMaximumFractionDigits(2);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 normalized() {
        /*
            Returns this Vector3 normalized
        */

        float mod = (float) Math.pow(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2), 0.5);
        if (mod == 0) {
            return new Vector3(0, 0 ,0);
        }
        return new Vector3(x/mod, y/mod, z/mod);
    }

    public Vector3 sub(Vector3 v) {
        /*
            Returns this Vector3 subbed by a Vector3 v
        */

        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    public Vector3 add(Vector3 v) {
        /*
            Returns this Vector3 summed to a Vector3 v
        */

        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    public Vector3 multiply(float a) {
        /*
            Returns this Vector3 multiplied by a scalar
        */

        return new Vector3(x * a, y * a, z * a);
    }

    public float dot(Vector3 v) {
        /*
            Returns the dot product between this Vector3 and a Vector3 v
        */

        return x * v.x + y * v.y + z * v.z;
    }

    public Vector3 cross(Vector3 v) {
        /*
            Returns the cross product between this Vector3 and a Vector3 v
        */

        return new Vector3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    public Vector3 rotateQuat(float x, float y, float z, float w) {
        /*
            Rotates the Vector3 by the given unit-length quaternion
        */

        Vector3 u = new Vector3(x, y, z);
        return ((u.multiply(2.0f * dot(u))).add(multiply(w*w - u.dot(u)))).add((u.cross(this)).multiply(2.0f * w));
    }

    @Override
    public String toString() {
        return "(" + df.format(x) + ", " + df.format(y) + ", " + df.format(z) + ")";
    }
}
