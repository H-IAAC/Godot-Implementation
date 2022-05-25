package com.example.godotrl.util;

import java.util.ArrayList;

public class State {
    Vector2 position;
    ArrayList<Vector2> closestCars;

    public State(Vector2 position, ArrayList<Vector2> closestCars) {
        this.position = position;
        this.closestCars = closestCars;
    }
}
