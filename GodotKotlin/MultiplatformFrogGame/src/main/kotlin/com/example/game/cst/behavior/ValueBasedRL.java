package com.example.game.cst.behavior;
import com.example.game.cst.behavior.Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public abstract class ValueBasedRL {
    protected Double ALPHA;
    protected Double GAMMA;
    protected Integer numActions;

    ValueBasedRL(Double ALPHA, Double GAMMA, Integer numActions) {
        this.ALPHA = ALPHA;
        this.GAMMA = GAMMA;
        this.numActions = numActions;
    }

    protected void update(ArrayList<Domain> state, ArrayList<Domain> newState,
                          Domain action, Domain reward) {
        /* TODO */
        return;
    }
    protected Double getValue(ArrayList<Domain> state, Domain action) {
        /* TODO */
        return new Double(-100);
    }

    protected ArrayList<Double> getValues(ArrayList<Domain> state) {
        return new ArrayList<Double>();
    }

    protected Integer getIdBestValue(ArrayList<Domain> state) {
        ArrayList<Double> vals = getValues(state);
        Double maxV = -Double.MIN_VALUE;
        Integer id = -1;
        for (Integer i = 0 ; i < vals.size(); i++) {
            if (! vals.get(i).isNaN() && vals.get(i).doubleValue() > maxV) {
                maxV = vals.get(i).doubleValue();
                id = i;
            }
        }
        return id;
    }

    protected Double getBestValue(ArrayList<Domain> state) {
        return Collections.max(getValues(state));
    }

    // could be Integer too.
    protected Domain epsilonGreedyPolicy(Double epsilon, ArrayList<Domain> state) {
        if (Math.random() < epsilon)
            return new Domain((int) Math.floor(Math.random() * this.numActions));
        else {
            return new Domain(getIdBestValue(state));
        }
    }

}
