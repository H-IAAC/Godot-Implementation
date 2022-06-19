package com.example.godotrl.cst.behavior;

import com.example.godotrl.util.Action;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FroggerFE extends FeaturesExtractor {
    private int yLen = 5;
    private int xLen = 5;

    public LinkedHashMap<String, Double> getFeatures( State state, Action action, Boolean isDone, Boolean hasWon ) {
        
        LinkedHashMap<String, Double> f = new LinkedHashMap<String, Double>();
        
        ArrayList<Vector2> closestCars = state.getClosestCars();
        Vector2 pos = state.getPosition();
        Double xCar, yCar;

        f.put("bias", 1.0);

        // y coord of the agent in the map (0 bottom)
        f.put("y-coord", pos.getY()/yLen);

        // x dist of the agent from the left border (0 at left)
        f.put("x-left-dist", pos.getX()/xLen);
        // x dist of the agent from the right border (0 at right)
        f.put("x-right-dist", (xLen - pos.getX()) /xLen);
        
        // position of the 1st closest car
        xCar = closestCars.get(0).getX() < 990 ? closestCars.get(0).getX() : xLen;
        yCar = closestCars.get(0).getY() < 990 ? closestCars.get(0).getY() : yLen;
        f.put("1st-car-x-coord", xCar/xLen);
        f.put("1st-car-y-coord", yCar/yLen);
        // position of the 2nd closest car
        xCar = closestCars.get(1).getX() < 990 ? closestCars.get(1).getX() : xLen;
        yCar = closestCars.get(1).getY() < 990 ? closestCars.get(1).getY() : yLen;
        f.put("2nd-car-x-coord", xCar/xLen);
        f.put("2nd-car-y-coord", yCar/yLen);
        // position of the 3rd closest car
        xCar = closestCars.get(2).getX() < 990 ? closestCars.get(2).getX() : xLen;
        yCar = closestCars.get(2).getY() < 990 ? closestCars.get(2).getY() : yLen;
        f.put("3rd-car-x-coord", xCar/xLen);
        f.put("3rd-car-y-coord", yCar/yLen);
        // position of the 4th closest car
        xCar = closestCars.get(3).getX() < 990 ? closestCars.get(3).getX() : xLen;
        yCar = closestCars.get(3).getY() < 990 ? closestCars.get(3).getY() : yLen;
        f.put("4th-car-x-coord", xCar/xLen);
        f.put("4th-car-y-coord", yCar/yLen);
        // position of the 5th closest car
        xCar = closestCars.get(4).getX() < 990 ? closestCars.get(4).getX() : xLen;
        yCar = closestCars.get(4).getY() < 990 ? closestCars.get(4).getY() : yLen;
        f.put("5th-car-x-coord", xCar/xLen);
        f.put("5th-car-y-coord", yCar/yLen);

        // if the agent won
        f.put("won", hasWon ? 1d : 0d );
        // if the agent lost
        f.put("lost", isDone && !hasWon ? 1d : 0d );

        // last movement of the agent
        f.put("moved-up", action.equals( Action.UP ) ? 1d : 0d );
        f.put("moved-right", action.equals( Action.RIGHT ) ? 1d : 0d );
        f.put("moved-down", action.equals( Action.DOWN ) ? 1d : 0d );
        f.put("moved-left", action.equals( Action.RIGHT ) ? 1d : 0d );

        return f;
    }
}
