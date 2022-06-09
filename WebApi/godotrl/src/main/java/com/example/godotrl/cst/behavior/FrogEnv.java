package com.example.godotrl.cst.behavior;

/* 1. Needs to implement reward policy */

import br.unicamp.cst.core.entities.MemoryObject;
import com.example.godotrl.util.Action;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

public class FrogEnv extends  Environment {

    Double yTarget = 10.0;

    public FrogEnv(MemoryObject stateMO, Domain[] actionSpace) {
        super(stateMO, actionSpace);
        this.actionSpace = actionSpace;
    }

    /*
     * create custom metric of reward
     */
    // private Double getReward( ArrayList<ArrayList<Domain>> state, Boolean isDone ) {
    //     Double sum = 0.0, x, y;
    //     for (ArrayList<Domain> dist : state) {
    //         x = dist.get(0).doubleValue();
    //         y = dist.get(1).doubleValue();
    //         if ( x == 0 && y == 0 )
    //             sum += -10.0;
    //         if ( Math.abs(x) <= 1 && Math.abs(y) <= 1 )
    //             sum += -1.0;
    //     }
    //     return sum;
    // }

    private Double getReward( ArrayList<Domain> state, Action lastAction, Boolean isDone ) {
        Double rw = 0.0;
        if ( state.get(0).doubleValue() == this.yTarget ) rw += 10.0;
        if ( lastAction == Action.UP ) rw += 1.0;
        if ( lastAction == Action.DOWN ) rw -= 1.0;
        if ( state.get(5).intValue() == 1 ) rw -= 10.0;

        return rw;
    }
    /*
    * calls a step in the environment.
    * Return ArrayList { state: ArrayList<Domain>, reward: Domain,
                          done: Boolean, info: String }
    * */
    public ArrayList step( State state, Action lastAction ) {
        Boolean isDone = this.isDone( state );
        ArrayList step = new ArrayList();
        ArrayList<Domain> obs = this.getObservationSpace( state, lastAction );
        step.add( obs );
        step.add( this.getReward( obs, lastAction, isDone ) );
        step.add( isDone );
        // step.add("info");

        return step;
    }

    public ArrayList getObservationSpace( State state, Action lastAction ) {
        if ( state == null )
            return new ArrayList<ArrayList<Domain>>();
        return this.convertState( state, lastAction );
    }

    private Boolean isDone( State st ) {

        if ( st.getClosestCars() != null ) {

            Vector2 pos = st.getPosition();
            ArrayList<Vector2> closestCars = st.getClosestCars();
            for ( Vector2 d : closestCars ) {

                if (d.getX().intValue() == pos.getX().intValue() &&
                    d.getY().intValue() == pos.getY().intValue() ) {
                    return true;
                }
            }
        }

        return false;
    }

    public Domain getActionID( Action action ) {
        Domain actionID;
        switch(action) {
            case UP:
                actionID = new Domain(0);
                break;
            case RIGHT:
                actionID = new Domain(1);
                break;
            case DOWN:
                actionID = new Domain(2);
                break;
            case LEFT:
                actionID = new Domain(3);
                break;
            default:
                actionID = new Domain(4);
                break;
        }

        return actionID;
    }

    public Action convertIdToAction  ( Domain actionID ) {
        Action action;
        switch( actionID.intValue() ) {
            case 0:
                action = Action.UP;
                break;
            case 1:
                action = Action.RIGHT;
                break;
            case 2:
                action = Action.DOWN;
                break;
            case 3:
                action = Action.LEFT;
                break;
            default:
                action = Action.INVALID;
                break;
        }

        return action;
    }


    // private ArrayList convertState( State st ) {

    //     ArrayList<ArrayList<Domain>> state = new ArrayList<>();

    //     Vector2 pos = st.getPosition();
    //     ArrayList<Vector2> closestCars = st.getClosestCars();

    //     for  (Vector2 car : closestCars ) {
    //         if ( car.getX() > -998.0 ) {
    //             ArrayList<Domain> dist = new ArrayList<>();
    //             dist.add( new Domain (pos.getX() - car.getX()) );
    //             dist.add( new Domain (pos.getY() - car.getY()) );
    //             state.add( dist );
    //         }
    //         else {
    //             state.add( this.getPos(car) );
    //         }
    //     }

    //     return state;
    // }

    private ArrayList convertState( State st, Action lastAction ) {

        Vector2 pos = st.getPosition();
        ArrayList<Vector2> closestCars = st.getClosestCars();

        Domain state[] = {
            new Domain( (lastAction == Action.UP ? 1 : 0) ),      // 0: agent height
            new Domain(0),            // 1: has Car up
            new Domain(0),            // 2: has Car right
            new Domain(0),            // 3: has Car down
            new Domain(0),            // 4: has Car left
            new Domain(0)             // 5: has Car in curr state
        };
                
        for  ( Vector2 car : closestCars ) {
            if ( car.getX() > -998.0 ) {
                Integer d[] = { 
                    (pos.getX().intValue() - car.getX().intValue() ),
                    (pos.getY().intValue() - car.getY().intValue() )
                };
                if (d[0] == 0 || d[1] == 0) {
                    if (d[1] == 1) state[1].val = 1;
                    else if (d[0] == 1) state[2].val = 1;
                    else if (d[1] == -1) state[3].val = 1;
                    else if (d[0] == -1) state[4].val = 1;
                    else state[5].val = 1;
                }
            }
        }

        ArrayList<Domain> obsSpace = new ArrayList<>(Arrays.asList( state ));
        return obsSpace;
    }

    private ArrayList<Domain> getPos( Vector2 v ) {
        ArrayList<Domain> pos = new ArrayList<>();
        pos.add( new Domain ( v.getX() ) );
        pos.add( new Domain ( v.getY() ) );
        return pos;
    }

    @Override
    public void render() {}

    // it is probably unnecessary
    @Override
    protected void extractMemoryObjects() {
        State state = ((State) super.stateMO.getI());
    }

    @Override
    public ArrayList<Domain> reset() {
        // communicator.reset();
        return (ArrayList<Domain>) this.stateMO.getI();
    }

    @Override
    public ArrayList step(Domain action) {
        return null;
    }
}