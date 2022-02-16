package com.example.game.cst.behavior;

import com.example.game.cst.behavior.ValueBasedRL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

public class LFA extends ValueBasedRL {
    private LinkedHashMap<String, Double> weights = new LinkedHashMap<String, Double>();
    private FeaturesExtractor extractor; // new featuresExtractor();

    public LFA(Double alpha, Double gamma, Integer numActions, FeaturesExtractor fe) {
        super(alpha, gamma, numActions);
        this.extractor = fe;
    }

    @Override
    public void update(ArrayList<Domain> state, ArrayList<Domain> newState,
                       Domain action, Domain reward) {
        LinkedHashMap<String, Double> gradient = this.extractor.getFeatures(state, action);

        Double new_q_val = this.getBestValue(newState);
        Double q_val = this.getValue(state, action);

        Double target = reward.doubleValue() + this.GAMMA * new_q_val;

        Set<String> name_features = gradient.keySet();
        for(String f : name_features) {
            Double w = this.ALPHA * (target - q_val) * gradient.get(f);
            this.weights.put(f, w);
        }
    }

    public LinkedHashMap<String, Double> getWeights() {
        return this.weights;
    }

    @Override
    protected Double getValue (ArrayList<Domain> state, Domain action) {
        Double q_val = (double) 0;
        LinkedHashMap<String, Double> gradient = this.extractor.getFeatures(state, action);

        Set<String> features = gradient.keySet();
        for (String f : features) {
            q_val += this.weights.get(f)*gradient.get(f);
        }

        return q_val;
    }

    @Override
    protected ArrayList<Double> getValues(ArrayList<Domain> state) {
        ArrayList<Double> vals = null;
        for (Integer a = 0; a < this.numActions; a++) {
            vals.add(getValue(state, new Domain(a)));
        }
        return vals;
    }


}