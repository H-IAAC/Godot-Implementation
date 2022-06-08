package com.example.godotrl.cst.behavior;

/*
 * Atenção: pode ser necessário alterar valor dos parâmetros de getFeatures (conferir FeaturesExtractor) */

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class LFA extends ValueBasedRL {
    private LinkedHashMap<String, Double> weights = new LinkedHashMap<String, Double>();
    private FeaturesExtractor extractor; // new featuresExtractor();

    public LFA(Double alpha, Double gamma, Integer numActions, String pathToSaveLearning, FeaturesExtractor fe) {
        super(alpha, gamma, numActions, pathToSaveLearning);
        this.extractor = fe;
    }

    @Override
    public void update(ArrayList state, ArrayList newState,
                       Domain action, Domain reward) {
        LinkedHashMap<String, Double> gradient = this.extractor.getFeatures(state, action);

        Double new_q_val = super.getBestValue(newState);
        Double q_val = super.getValue(state, action);

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

    @Override
    protected Double getValue (ArrayList state, Domain action) {
        Double q_val = (double) 0;
        LinkedHashMap<String, Double> gradient = this.extractor.getFeatures(state, action);

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

    protected void serializeLearning(String fileName) {
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(super.pathToSaveLearning + fileName);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.weights);

            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void deserializeLearning(String fileName) {
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(super.pathToSaveLearning + fileName);
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(fileInputStream);
            //TODO("Figure out what's the right way to handle objectInputStream")
            //objectInputStream.readObject(this.weights);

            objectInputStream.close();
            fileInputStream.close();
        }
        catch(IOException e1) {
            e1.printStackTrace();
        }
//        catch (ClassNotFoundException e2) {
//            System.out.println("Class not found");
//            e2.printStackTrace();
//        }
    }
}
