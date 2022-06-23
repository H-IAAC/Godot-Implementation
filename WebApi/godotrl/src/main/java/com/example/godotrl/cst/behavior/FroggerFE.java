package com.example.godotrl.cst.behavior;

import com.example.godotrl.util.Action;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FroggerFE extends FeaturesExtractor {

    private Double yLen;
    private Double xLen;

    private Double factor = 0.1;

    public FroggerFE(Double xLen, Double yLen ) {
        this.yLen = yLen;
        this.xLen = xLen;
    }


    public LinkedHashMap<String, Double> getFeatures( State state, Action action, Boolean isDone, Boolean hasWon ) {
        
        LinkedHashMap<String, Double> f = new LinkedHashMap<String, Double>();

        Double xCar, yCar;
        ArrayList<Vector2> closestCars = state.getClosestCars();
        Vector2 pos = state.getPosition();
        Double px = pos.getX(), py = pos.getY();
        Double dx = px, dy = py;

        switch (action) {
            case UP:
                dy = Math.max( 0.0, py-1 );
                break;
            case LEFT:
                dx = Math.max( 0.0, px-1 );
                break;
            case DOWN:
                dy = Math.min( yLen-1, py+1 );
                break;
            case RIGHT:
                dx = Math.min( xLen-1, px+1 );
                break;
            default:
                break;
        }

        Boolean won = ( dy == 0 ), lost = false;
        for ( Vector2 car : closestCars ) {
            if ( car.getY() == dy && ( car.getX() == dx || car.getX() + 1 == dx ) )
                lost = true;
        }

        f.put("bias", 1.0);

        // y coord of the agent in the map (0 bottom)
        f.put("y-coord", (yLen - dy)/yLen);

        // x dist of the agent from the left border (0 at left)
        f.put("x-coord", (xLen - dx)/xLen);

        String num;
        for ( int i = 0; i < closestCars.size(); i++ ) {
            num = Integer.toString(i);
            xCar = closestCars.get(0).getX() > -990 ? closestCars.get(0).getX() : xLen;
            yCar = closestCars.get(0).getY() > -990 ? closestCars.get(0).getY() : yLen;
            // f.put(num+"th-car-x-coord", (xLen - xCar)/xLen );
            // f.put(num+"th-car-x-after", (xLen - xCar + 1)/xLen );
            // f.put(num+"th-car-y-coord", (yLen - yCar)/yLen );
            f.put(num+"th-x-dist", 1.0 - Math.abs( (xCar - dx)/xLen ) );
            f.put(num+"th-x-dist-after", 1.0 - Math.abs( (xCar + 1 - dx)/xLen ) );
            f.put(num+"th-y-dist", 1.0 - Math.abs( (yCar - dy)/yLen ) );
        }

        // if the agent won
        f.put("won", won ? 1d : 0d );
        // if the agent lost
        f.put("lost", won || lost ? 1d : 0d );

        // last movement of the agent
        f.put("moved-up", action.equals( Action.UP ) ? 1d : 0d );
        f.put("moved-right", action.equals( Action.RIGHT ) ? 1d : 0d );
        f.put("moved-down", action.equals( Action.DOWN ) ? 1d : 0d );
        f.put("moved-left", action.equals( Action.RIGHT ) ? 1d : 0d );

        f.replaceAll( ( k, v ) -> v * factor );
        return f;
    }
}
