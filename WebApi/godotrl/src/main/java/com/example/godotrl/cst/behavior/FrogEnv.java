package com.example.godotrl.cst.behavior;

/* 1. Needs to implement reward policy */

import br.unicamp.cst.core.entities.MemoryObject;
import com.example.godotrl.util.Action;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

public class FrogEnv extends  Environment {

    Double yLen;

    public FrogEnv(MemoryObject stateMO, Domain[] actionSpace, Double yLen) {
        super(stateMO, actionSpace);
        this.actionSpace = actionSpace;
        this.yLen = yLen;
    }

    /* Metrics created for defining reward policy */
    private Double getReward( ArrayList<Domain> state, Vector2 pos, Action lastAction, Boolean isDone, Boolean hasWon ) {
        Double rw = 0.0;
        rw += (double) ( yLen - 1.0 - pos.getY() )/(100 * yLen);
        if ( isDone ) {
            if ( hasWon ) {
                rw += yLen * 5.0;
            }
            else {
                rw -= yLen * 3.0;
            }
        }
        return rw;
    }

    /*
    * calls a step in the environment.
    * Return ArrayList { state: ArrayList<Domain>, reward: Domain,
                          done: Boolean, info: String }
    * */
    public ArrayList step( State state, Action lastAction, Boolean isDone, Boolean hasWon ) {
        Vector2 pos = state.getPosition();
        ArrayList step = new ArrayList();
        /* IS THIS CORRECT? */
        ArrayList<Domain> obs = this.getObservationSpace( state, lastAction );
        step.add( obs );
        step.add( this.getReward( obs, pos, lastAction, isDone, hasWon ) );
        step.add( isDone );

        return step;
    }

    public ArrayList getObservationSpace( State state, Action lastAction ) {
        return this.convertState( state, lastAction );
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

    private ArrayList convertState( State st, Action lastAction ) {

        Vector2 pos = st.getPosition();
        ArrayList<Vector2> closestCars = st.getClosestCars();

        Domain state[] = {
            new Domain( (lastAction == Action.UP ? 1 : 0) ),      // 0: agent is closer of goal
            new Domain(0),            // 1: has Car up
            new Domain(0),            // 2: has Car right
            new Domain(0),            // 3: has Car down
            new Domain(0),            // 4: has Car left
            new Domain(0),            // 5: has Car top left
            new Domain(0),            // 6: has Car top right
            new Domain(0),            // 7: has Car lower right
            new Domain(0),            // 8: has Car lower left
            new Domain(0)             // 9: has Car at second left
            // new Domain(0)          // 10: has Car in curr state
        };
                
        for  ( Vector2 car : closestCars ) {
            if ( car.getX() > -998.0 ) {
                Integer d[] = { 
                    ( pos.getX().intValue() - car.getX().intValue() ),
                    ( pos.getY().intValue() - car.getY().intValue() )
                };
                // agent at left -> car at right
                if ( d[0] == -1 ) {
                    // agent at the top -> car lower the agent
                    if ( d[1] == -1 ) state[7].val = 1;
                    else if ( d[1] == 0 ) state[2].val = 1;
                    else if ( d[1] == 1 ) state[6].val = 1;
                }

                // agent and car at the same x-axis
                else if ( d[0] == 0 ) {
                    // agent at the top -> car lower the agent
                    if ( d[1] == -1 ) state[3].val = 1;
                    else if ( d[1] == 1 ) state[1].val = 1;
                }

                // agent at right -> car at left
                else if ( d[0] == 1 ) {
                    // agent at the top -> car lower the agent
                    if ( d[1] == -1 ) state[8].val = 1;
                    else if ( d[1] == 0 ) state[4].val = 1;
                    else if ( d[1] == 1 ) state[5].val = 1;
                }

                // agent is two squares right a car
                else if ( d[0] == 2 ) {
                    if ( d[1] == 0 ) state[9].val = 1;
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