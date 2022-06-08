package com.example.godotrl.cst.behavior;

/* 1. Needs to implement reward policy */

import br.unicamp.cst.core.entities.MemoryObject;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;

public class FrogEnv extends  Environment {

    public FrogEnv(MemoryObject stateMO, Domain[] actionSpace) {
        super(stateMO, actionSpace);
        this.actionSpace = actionSpace;
    }

    /*
     * create custom metric of reward
     */
    private Domain getReward( State state, Boolean isDone ) {
        Domain reward = new Domain<>(0);
        // TODO
        return reward;
    }

    /*
    * calls a step in the environment.
    * Return ArrayList { state: ArrayList<Domain>, reward: Domain,
                          done: Boolean, info: String }
    * */
    @Override
    public ArrayList step(Domain action) {
        // boolean isDone = communicator.step(action.intValue());
        boolean isDone = true;
        ArrayList step = new ArrayList();
        step.add( this.getObservationSpace() );
        step.add(this.getReward( (State)this.stateMO.getI(), isDone) );
        step.add(isDone);
        step.add("info");

        return step;
    }

    public ArrayList step() {
        Boolean isDone = this.isDone();
        ArrayList step = new ArrayList();
        step.add( this.getObservationSpace() );
        step.add( this.getReward( (State) this.stateMO.getI(), isDone ) );
        step.add( isDone );
        step.add("info");

        return step;
    }

    public ArrayList getObservationSpace( State state ) {
        return this.convertState( state );
    }

    private ArrayList<Domain> getPos( Vector2 v ) {
        ArrayList<Domain> pos = new ArrayList<>();
        pos.add( new Domain ( v.getX() ) );
        pos.add( new Domain ( v.getY() ) );
        return pos;
    }

    private Boolean isDone() {
        State st = (State) this.stateMO.getI();
        for ( Vector2 d : st.getClosestCars() ) {
            if ( d.getX() == 0 && d.getY() == 0 ) {
                return true;
            }
        }
        return false;
    }
    private ArrayList convertState( State st ) {

        // ArrayList<Domain> pos = getPos ( st.getPosition() );
        ArrayList<ArrayList<Domain>> cars = new ArrayList<>();
        // ArrayList<ArrayList<ArrayList<Domain>>> state = new ArrayList<>();

        ArrayList<Vector2> closestCars = st.getClosestCars();
        for  (Vector2 v : closestCars ) {
            cars.add( this.getPos(v) );
        }
        // state.add(pos);
        // state.add(cars);
        // return state;

        return cars;
    }

    @Override
    public void render() {
        // communicator.render();
    }

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
}