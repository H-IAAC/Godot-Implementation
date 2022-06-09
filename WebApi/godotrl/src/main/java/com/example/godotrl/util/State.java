package com.example.godotrl.util;

import java.util.ArrayList;

public class State {
    Vector2 position = new Vector2(0,0);
    ArrayList<Vector2> closestCars = new ArrayList<>();

    public State(Vector2 position, ArrayList<Vector2> closestCars) {
        this.position = position;
        this.closestCars = closestCars;
    }

    public Vector2 getPosition() {
        return position;
    }

    public ArrayList<Vector2> getClosestCars() {
        return closestCars;
    }
}
