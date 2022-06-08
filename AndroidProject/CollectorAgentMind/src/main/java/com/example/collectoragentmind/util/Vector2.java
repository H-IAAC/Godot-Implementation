package com.example.collectoragentmind.util;

public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Vector2 v) {
        return Math.pow((Math.pow(x - v.x, 2) + Math.pow(y - v.y, 2)), 0.5);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
