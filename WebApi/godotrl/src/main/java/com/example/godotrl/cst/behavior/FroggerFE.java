package com.example.godotrl.cst.behavior;

import com.example.godotrl.util.Action;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class FroggerFE extends FeaturesExtractor {

    private Double yLen;
    private Double xLen;
    private Integer numMaxSteps;

    private Double factor = 1.0;
    private Integer stateQueueSize = 4;

    protected LinkedList<State> stateQueue = new LinkedList<>();

    public FroggerFE(Double xLen, Double yLen, Integer numMaxSteps ) {
        this.yLen = yLen;
        this.xLen = xLen;
        this.numMaxSteps = numMaxSteps;
    }

    public LinkedHashMap<String, Double> getFeatures( State state, Action action, Integer currStep, Boolean isDone, Boolean hasWon ) {
        
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

        Boolean won = (hasWon || py == 0 ), lost = false, willWin = (dy == 0), willLose = false;
        for ( Vector2 car : closestCars ) {
            if ( (int) car.getY().intValue() == (int) py.intValue() &&
                    (int) car.getX().intValue() == (int) px.intValue() ||
                    (isDone && !hasWon) )
                lost = true;
            if ( (int) car.getY().intValue() == (int) dy.intValue() &&
                    ( (int) car.getX().intValue() == (int) dx.intValue() || (int)(car.getX().intValue() + 1) == (int) dx.intValue() ) )
                willLose = true;
        }

        f.put("bias", 1.0);

        // y coord of the agent in the map (0 bottom)
        f.put("y-coord", (yLen - py)/yLen);
        f.put("y-coord-after", (yLen - dy)/yLen);

        // x dist of the agent from the left border (0 at left)
        f.put("x-coord", (xLen - px)/xLen);
        f.put("x-coord-after", (xLen - dx)/xLen);

        f.put("dist-to-target", py/yLen );

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
            // xCar = closestCars.get(0).getX() > -990 ? closestCars.get(0).getX() : 1000;
            // yCar = closestCars.get(0).getY() > -990 ? closestCars.get(0).getY() : 1000;

            f.put(num+"th-car-x-coord", xCar<-990?0.0: (xLen - xCar)/xLen );
            f.put(num+"th-car-x-after", xCar<-990?0.0: (xLen - xCar + 1)/xLen );
            f.put(num+"th-car-y-coord", yCar<-990?0.0: (yLen - yCar)/yLen );
            f.put(num+"th-x-dist", xCar<-990?0.0: 1.0 - Math.abs( (xCar - dx)/xLen ) );
            f.put(num+"th-x-dist-after", xCar<-990?0.0: 1.0 - Math.abs( (xCar + 1 - dx)/xLen ) );
            f.put(num+"th-y-dist", yCar<-990?0.0: 1.0 - Math.abs( (yCar - dy)/yLen ) );

            if ( xCar > -990.0 ) {
                Integer d[] = {
                        ( xCar.intValue() - px.intValue() ),
                        ( yCar.intValue() - py.intValue() )
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

        // if the agent won
        f.put("won", won ? 1d : 0d );
        // if the agent lost
        f.put("lost", lost ? 1d : 0d );
        f.put("will-win", willWin ? 1d : 0d );
        f.put("will-lose", willLose ? 1d : 0d );


        // last movement of the agent
        f.put("moved-up", action.equals( Action.UP ) ? 1d : 0d );
        f.put("moved-right", action.equals( Action.RIGHT ) ? 1d : 0d );
        f.put("moved-down", action.equals( Action.DOWN ) ? 1d : 0d );
        f.put("moved-left", action.equals( Action.RIGHT ) ? 1d : 0d );

        f.put("curr-step", (double) currStep / numMaxSteps );

        f.replaceAll( ( k, v ) -> v * factor );
        return f;
    }

    // public LinkedHashMap<String, Double> getFeatures(
    //      State state, Action action, Boolean isDone, Boolean hasWon Integer currStep ) {

    //     stateQueue.addLast(state);
    //     if ( stateQueue.size() > stateQueueSize ) {
    //         stateQueue.removeFirst();
    //     }

    //     LinkedHashMap<String, Double> f = new LinkedHashMap<String, Double>();

    //     Double xCar, yCar;
    //     ArrayList<Vector2> closestCars = state.getClosestCars();
    //     Vector2 pos = state.getPosition();
    //     Double px = pos.getX(), py = pos.getY();
    //     Double dx = px, dy = py;

    //     switch (action) {
    //         case UP:
    //             dy = Math.max( 0.0, py-1 );
    //             break;
    //         case LEFT:
    //             dx = Math.max( 0.0, px-1 );
    //             break;
    //         case DOWN:
    //             dy = Math.min( yLen-1, py+1 );
    //             break;
    //         case RIGHT:
    //             dx = Math.min( xLen-1, px+1 );
    //             break;
    //         default:
    //             break;
    //     }

    //     Boolean willWin = (dy == 0), willLose = false;
    //     for ( Vector2 car : closestCars ) {
    //         if ( (int) car.getY().intValue() == (int) dy.intValue() &&
    //                 ( (int) car.getX().intValue() == (int) dx.intValue() || (int)(car.getX().intValue() + 1) == (int) dx.intValue() ) )
    //             willLose = true;
    //     }

    //     f.put("bias", 1.0);

    //     // y coord of the agent in the map (0 bottom)
    //     f.put("y-coord", (yLen - py)/yLen);
    //     f.put("y-coord-after-movement", (yLen - dy)/yLen);

    //     // x dist of the agent from the left border (0 at left)
    //     f.put("x-coord", (xLen - px)/xLen);
    //     f.put("x-coord-after-movement", (xLen - dx)/xLen);

    //     f.put("dist-to-target", py/yLen );

    //     // view cars arround the agent in current and previous movements
    //     for ( int s = 0; s < stateQueueSize; s++ ) {

    //         String st = Integer.toString(s+1);

    //         f.put(st+"th-state-has-car-up", 0.0);
    //         f.put(st+"th-state-has-car-right", 0.0);
    //         f.put(st+"th-state-has-car-down", 0.0);
    //         f.put(st+"th-state-has-car-left", 0.0);
    //         f.put(st+"th-state-has-car-upper-right", 0.0);
    //         f.put(st+"th-state-has-car-lower-right", 0.0);
    //         f.put(st+"th-state-has-car-lower-left", 0.0);
    //         f.put(st+"th-state-has-car-upper-left", 0.0);
    //         f.put(st+"th-state-has-car-second-left", 0.0);

    //         if ( s < stateQueue.size() ) {

    //             State currState = stateQueue.get(s);
    //             Double _px = currState.getPosition().getX();
    //             Double _py = currState.getPosition().getY();

    //             for (int i = 0; i < currState.getClosestCars().size(); i++) {

    //                 xCar = currState.getClosestCars().get(i).getX();
    //                 yCar = currState.getClosestCars().get(i).getY();

    //                 if (xCar > -990.0) {
    //                     Integer d[] = {
    //                             (xCar.intValue() - _px.intValue()),
    //                             (yCar.intValue() - _py.intValue())
    //                     };
    //                     // car at left
    //                     if (d[0] == -1) {
    //                         if (d[1] == -1) f.put(st + "th-state-has-car-upper-left", 1.0);
    //                         else if (d[1] == 0) f.put(st + "th-state-has-car-left", 1.0);
    //                         else if (d[1] == 1) f.put(st + "th-state-has-car-lower-left", 1.0);
    //                     }

    //                     // agent and car at the same x-axis
    //                     else if (d[0] == 0) {
    //                         if (d[1] == -1) f.put(st + "th-state-has-car-up", 1.0);
    //                         else if (d[1] == 1) f.put(st + "th-state-has-car-down", 1.0);
    //                     }

    //                     // car at right
    //                     else if (d[0] == 1) {
    //                         if (d[1] == -1) f.put(st + "th-state-has-car-upper-right", 1.0);
    //                         else if (d[1] == 0) f.put(st + "th-state-has-car-right", 1.0);
    //                         else if (d[1] == 1) f.put(st + "th-state-has-car-lower-right", 1.0);
    //                     }

    //                     // car is two squares left the agent
    //                     else if (d[0] == -2) {
    //                         if (d[1] == 0) f.put(st + "th-state-has-car-second-left", 1.0);
    //                     }
    //                 }
    //             }
    //         }
    //     }


    //     String num;
    //     // to read the distance from each car.
    //     for ( int i = 0; i < closestCars.size(); i++ ) {

    //         num = Integer.toString(i+1);
    //         xCar = closestCars.get(i).getX();
    //         yCar = closestCars.get(i).getY();

    //         f.put(num+"th-car-x-coord", xCar<-990?0.0: (xLen - xCar)/xLen );
    //         f.put(num+"th-car-x-after-agent-movement", xCar<-990?0.0: (xLen - xCar + 1)/xLen );
    //         f.put(num+"th-car-y-coord", yCar<-990?0.0: (yLen - yCar)/yLen );
    //         f.put(num+"th-x-dist", xCar<-990?0.0: 1.0 - Math.abs( (xCar - dx)/xLen ) );
    //         f.put(num+"th-x-dist-after-agent-movement", xCar<-990?0.0: 1.0 - Math.abs( (xCar + 1 - dx)/xLen ) );
    //         f.put(num+"th-y-dist", yCar<-990?0.0: 1.0 - Math.abs( (yCar - dy)/yLen ) );
    //     }

    //     // result of the episode
    //     f.put("won", hasWon ? 1d : 0d );
    //     f.put("lost", isDone && !hasWon ? 1d : 0d );
    //     f.put("will-win", willWin ? 1d : 0d );
    //     f.put("will-lose", willLose ? 1d : 0d );

    //     // last movement of the agent
    //     f.put("moved-up", action.equals( Action.UP ) ? 1d : 0d );
    //     f.put("moved-right", action.equals( Action.RIGHT ) ? 1d : 0d );
    //     f.put("moved-down", action.equals( Action.DOWN ) ? 1d : 0d );
    //     f.put("moved-left", action.equals( Action.RIGHT ) ? 1d : 0d );
    //     f.put("has-moved", px != dx || py != dy ? 1d : 0d );

    //     f.put("curr-step", (double) currStep / numMaxSteps );


    //     f.replaceAll( ( k, v ) -> v * factor );
    //     return f;
    // }
}
