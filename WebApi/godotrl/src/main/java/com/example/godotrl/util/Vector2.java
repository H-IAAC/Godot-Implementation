package com.example.godotrl.util;

public class Vector2 {
    private double x;
    private double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 v) {
        x += v.x;
        y += v.y;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public double manhattanDistance(Vector2 v) {
        return Math.abs(v.x - x) + Math.abs(v.y - y);
    }

    @Override
    public String toString() {
        return String.format("[%.2f, %.2f]", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector2)) {
            return false;
        }

        Vector2 v = (Vector2) o;
        return x == v.x && y == v.y;
    }
}
