package com.example.godotrl.cst.behavior;

/*1. iniciar tabela-Q com valores com desvio-padrão definido
 * */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Tabular extends ValueBasedRL {

    private HashMap<ArrayList, ArrayList<Double>> qTable;

    //constructor
    public Tabular(Double alpha, Double gamma, Integer numActions, String pathToSaveLearning) {
        super(alpha, gamma, numActions, pathToSaveLearning);
    }

    protected void initQValue(ArrayList state) {
        ArrayList<Double> initVals = new ArrayList<Double>();
        double mean = 0.5, std = 0.05;
        Random rdm = new Random();
        for (int i = 0; i < super.numActions; i++) {
            initVals.add( mean + std * rdm.nextGaussian() );
        }

        qTable.put(state, initVals);
    }

    protected Double getValue(ArrayList state, Integer idAction) {
        ArrayList<Double> val = qTable.get(state);
        if (val == null)
            initQValue(state);

        return qTable.get(state).get(idAction);
    }

    @Override
    protected ArrayList<Double> getValues(ArrayList state) {
        if (state == null) {
            initQValue( state );
        }
        /*NULLPOINTER EXCEPTION*/
        else if (!this.qTable.containsKey(state))
            initQValue( state );

        return qTable.get(state);
    }

    @Override
    protected void update (ArrayList state, ArrayList newState, Domain action, Domain reward) {
        Integer idAction = action.intValue();
        Double maxFutureQ = super.getBestValue(newState);
        ArrayList<Double> qValues = super.getValues(state);
        Double qVal = qValues.get(idAction);
        qVal += super.ALPHA * (reward.doubleValue() + super.GAMMA * maxFutureQ - qVal);
        qValues.set(idAction, qVal);
        // perhaps it is not even necessary! Passed my reference.
        this.qTable.put(state, qValues);
    }

    protected void serializeLearning(String fileName) {
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(super.pathToSaveLearning + fileName);
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.qTable);

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
            this.qTable =
                    (HashMap)objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        }
        catch(IOException e1) {
            e1.printStackTrace();
        }
        catch(ClassNotFoundException e2) {
            System.out.println("Class not found");
            e2.printStackTrace();
        }
    }
}
