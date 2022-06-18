package com.example.godotrl.cst.behavior;

import java.util.ArrayList;
import java.util.Collections;

public abstract class ValueBasedRL {
    protected Double ALPHA;
    protected Double GAMMA;
    protected Integer numActions;
    protected String pathToSaveLearning;

    ValueBasedRL(Double ALPHA, Double GAMMA, Integer numActions, String pathToSaveLearning) {
        this.ALPHA = ALPHA;
        this.GAMMA = GAMMA;
        this.numActions = numActions;
        this.pathToSaveLearning = pathToSaveLearning;
    }

    /* updates your learning table (Q-table or linear function weights) */
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

    public Double getBestValue(ArrayList<Domain> state) {
        return Collections.max(getValues(state));
    }

    // could be Integer too.
    protected Domain epsilonGreedyPolicy(Double epsilon, ArrayList<Domain> state) {
        if (Math.random() < epsilon)
            return new Domain((int) Math.floor( ((Math.random() - 0.1) * (this.numActions - 1) ) ) );
        else {
            return new Domain(getIdBestValue(state));
        }
    }

    /* save your learning content (Q-table or linear function weights) is a byte stream format */
    protected abstract void serializeLearning(String fileName);
    /* load your learning content (Q-table or linear function weights) from a byte stream format */
    protected abstract void deserializeLearning(String fileName);
}
