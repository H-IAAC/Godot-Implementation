package com.example.collectoragentmind.util;

public class Apple {
    public String name;
    public Vector2 position;

    public Apple(String name, Vector2 position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return name + " - (" + position + ")";
    }
}
