package com.example.game.cst.behavior;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FeaturesExtractor {
    public LinkedHashMap<String, Double> getFeatures(ArrayList<Domain> state, Domain action) {
        LinkedHashMap<String, Double> f = new LinkedHashMap<String, Double>();

        f.put("bias", 1.0);
        /*
         * create features
         * */

        return f;
    }
}