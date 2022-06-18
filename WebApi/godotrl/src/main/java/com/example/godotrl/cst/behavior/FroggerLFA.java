package com.example.godotrl.cst.behavior;

import com.example.godotrl.util.Action;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

public class FroggerLFA extends LFA {

    public FroggerLFA(Double alpha, Double gamma, Integer numActions, String pathToSaveLearning, FroggerFE fe) {
        super(alpha, gamma, numActions, pathToSaveLearning, fe);
    }

    public void update(State state, State newState, Action action, Domain reward, Boolean isDone, Boolean won) {
        LinkedHashMap<String, Double> gradient = ((FroggerFE) this.extractor).getFeatures(state, action, isDone, won);

        Double new_q_val = this.getBestValue(newState);
        Double q_val = this.getValue(state, action, isDone, won);

        Double target = reward.doubleValue() + super.GAMMA * new_q_val;

        Set<String> name_features = gradient.keySet();
        for(String f : name_features) {
            Double w = super.ALPHA * (target - q_val) * gradient.get(f);
            this.weights.put(f, w);
        }
    }

    public LinkedHashMap<String, Double> getWeights() {
        return this.weights;
    }

    private Double getBestValue(State state) {
        ArrayList<Double> futureVals = new ArrayList<>();
        for (Action _action : Action.values() ) {
            int predictStep = isTheEnd(state, _action);
            Boolean done = predictStep > 0;
            Boolean won = done && predictStep == 1;
            futureVals.add( this.getValue(state, _action, done, won) );
        }
        return Collections.max( futureVals );
    }

    private int isTheEnd(State state, Action action ) {
        Double x = state.getPosition().getX();
        Double y = state.getPosition().getY();
        // won
        if ( y == 9.0 && action == Action.UP )
            return 1;

        switch (action) {
            case UP:
                y = Math.min(10.0, y+1);
                break;
            case RIGHT:
                x = Math.min( 10.0, x+1 );
                break;
            case DOWN:
                y = Math.max(y-1, 0);
                break;
            case LEFT:
                x = Math.max(0.0, x-1);
                break;
        }
        for (Vector2 car : state.getClosestCars() ) {
            if ( car.getX() == x-1 && car.getY() == y )
                return 2;
        }

        return 0;
    }

    protected Double getValue (State state, Action action, Boolean isDone, Boolean won) {
        Double q_val = (double) 0;
        LinkedHashMap<String, Double> gradient = ((FroggerFE) this.extractor).getFeatures(state, action, isDone, won);

        Set<String> features = gradient.keySet();
        for (String f : features) {
            q_val += this.weights.get(f)*gradient.get(f);
        }

        return q_val;
    }

    @Override
    protected ArrayList<Double> getValues(ArrayList state) {
        ArrayList<Double> vals = null;
        for (Integer a = 0; a < super.numActions; a++) {
            vals.add(getValue(state, new Domain(a)));
        }
        return vals;
    }

}
