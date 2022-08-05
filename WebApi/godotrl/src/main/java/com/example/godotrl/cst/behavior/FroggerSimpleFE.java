package com.example.godotrl.cst.behavior;

import com.example.godotrl.util.Action;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FroggerSimpleFE extends FroggerFE {

    public FroggerSimpleFE(Double xLen, Double yLen, Integer numMaxSteps ) {
        super(xLen, yLen, numMaxSteps);
    }
    @Override
    public LinkedHashMap<String, Double> getFeatures(State state, Action action, Integer currStep, Boolean isDone, Boolean hasWon ) {

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

        Boolean willWin = (dy == 0), willLose = false;
        for ( Vector2 car : closestCars ) {
            if ( Double.compare(car.getY(), dy) == 0 &&
                    ( Double.compare(car.getX(), dx) == 0 || Double.compare(car.getX() + 1, dx) == 0 ) )
                willLose = true;
        }

        f.put("bias", 1.0);

        // y coord of the agent in the map (0 bottom)
        f.put("y-coord", (yLim - py)/yLim);
        f.put("y-coord-after", (yLim - dy)/yLim);

        // x dist of the agent from the left border (0 at left)
        f.put("x-coord", (xLim - px)/xLim);
        f.put("x-coord-after", (xLim - dx)/xLim);

        f.put("dist-to-target", py/yLim );
        
        // received state
        f.put("had-car-up", 0.0);
        f.put("had-car-right", 0.0);
        f.put("had-car-down", 0.0);
        f.put("had-car-left", 0.0);
        f.put("had-car-upper-right", 0.0);
        f.put("had-car-lower-right", 0.0);
        f.put("had-car-lower-left", 0.0);
        f.put("had-car-upper-left", 0.0);
        f.put("had-car-second-left", 0.0);

        // new state
        f.put("has-car-up", 0.0);
        f.put("has-car-right", 0.0);
        f.put("has-car-down", 0.0);
        f.put("has-car-left", 0.0);
        f.put("has-car-upper-right", 0.0);
        f.put("has-car-lower-right", 0.0);
        f.put("has-car-lower-left", 0.0);
        f.put("has-car-upper-left", 0.0);
        f.put("has-car-second-left", 0.0);

        String num;
        for ( int i = 1; i <= closestCars.size(); i++ ) {
            num = Integer.toString(i);
            xCar = closestCars.get(i-1).getX();
            yCar = closestCars.get(i-1).getY();

            f.put(num+"th-car-x-coord", xCar<-990?0.0: (xLim - xCar)/xLim );
            f.put(num+"th-car-x-after", xCar<-990?0.0: (xLim - xCar + 1)/xLim );
            f.put(num+"th-car-y-coord", yCar<-990?0.0: (yLim - yCar)/yLim );
            f.put(num+"th-x-dist", xCar<-990?0.0: 1.0 - Math.abs( (xCar - dx)/xLim ) );
            f.put(num+"th-x-dist-after", xCar<-990?0.0: 1.0 - Math.abs( (xCar + 1 - dx)/xLim ) );
            f.put(num+"th-y-dist", yCar<-990?0.0: 1.0 - Math.abs( (yCar - dy)/yLim ) );
            
           // check cars positions at the received state 
            if ( Double.compare(xCar, -990.0) > 0 ) {
                Integer d[] = {
                        ( xCar.intValue() - px.intValue() ),
                        ( yCar.intValue() - py.intValue() )
                };
                // car at left
                if ( d[0] == -1 ) {
                    if ( d[1] == -1 ) f.put("had-car-upper-left", 1.0);
                    else if ( d[1] == 0 ) f.put("had-car-left", 1.0);
                    else if ( d[1] == 1 ) f.put("had-car-lower-left", 1.0);
                }

                // agent and car at the same x-axis
                else if ( d[0] == 0 ) {
                    if ( d[1] == -1 ) f.put("had-car-up", 1.0);
                    else if ( d[1] == 1 ) f.put("had-car-down", 1.0);
                }

                // car at right
                else if ( d[0] == 1 ) {
                    if ( d[1] == -1 ) f.put("had-car-upper-right", 1.0);
                    else if ( d[1] == 0 ) f.put("had-car-right", 1.0);
                    else if ( d[1] == 1 ) f.put("had-car-lower-right", 1.0);
                }

                // car is two squares left the agent
                else if ( d[0] == -2 ) {
                    if ( d[1] == 0 ) f.put("had-car-second-left", 1.0);
                }
            }
            
            // check cars positions at the new state
            if ( Double.compare(xCar, -990.0) > 0 ) {
                Integer d[] = {
                        ( xCar.intValue() - dx.intValue() ),
                        ( yCar.intValue() - dy.intValue() )
                };
                // car at left
                if ( d[0] == -1 ) {
                    if ( d[1] == -1 ) f.put("has-car-upper-left", 1.0);
                    else if ( d[1] == 0 ) f.put("has-car-left", 1.0);
                    else if ( d[1] == 1 ) f.put("has-car-lower-left", 1.0);
                }

                // agent and car at the same x-axis
                else if ( d[0] == 0 ) {
                    if ( d[1] == -1 ) f.put("has-car-up", 1.0);
                    else if ( d[1] == 1 ) f.put("has-car-down", 1.0);
                }

                // car at right
                else if ( d[0] == 1 ) {
                    if ( d[1] == -1 ) f.put("has-car-upper-right", 1.0);
                    else if ( d[1] == 0 ) f.put("has-car-right", 1.0);
                    else if ( d[1] == 1 ) f.put("has-car-lower-right", 1.0);
                }

                // car is two squares left the agent
                else if ( d[0] == -2 ) {
                    if ( d[1] == 0 ) f.put("has-car-second-left", 1.0);
                }
            }
        }

        // final result of the game
        f.put("won", hasWon ? 1d : 0d );
        f.put("lost", isDone&&!hasWon ? 1d : 0d );
        f.put("will-win", willWin ? 1d : 0d );
        f.put("will-lose", willLose ? 1d : 0d );

        // last movement of the agent
        f.put("moved-up", action.equals( Action.UP ) ? 1d : 0d );
        f.put("moved-right", action.equals( Action.RIGHT ) ? 1d : 0d );
        f.put("moved-down", action.equals( Action.DOWN ) ? 1d : 0d );
        f.put("moved-left", action.equals( Action.LEFT ) ? 1d : 0d );
        f.put("has-moved", Double.compare(px, dx) != 0 || Double.compare(py, dy) != 0 ? 1d : 0d );

        f.put("curr-step", (double) currStep / numMaxSteps );

        f.replaceAll( ( k, v ) -> v * super.factor );
        return f;
    }
}
