package com.example.godotrl.cst.behavior;

/*1. iniciar tabela-Q com valores com desvio-padrão definido
 * */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Tabular extends ValueBasedRL {

    private ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Double>>>>>> qTable = new ArrayList<>();

    //constructor
    public Tabular(Double alpha, Double gamma, Integer numActions, String pathToSaveLearning) {
        super(alpha, gamma, numActions, pathToSaveLearning);
    }

    protected void initQTable() {
        // is closer of goal
        for (int cl = 0; cl <= 1; cl++ ) {
            // has car up
            for ( int up = 0; up <= 1; up++ ) {

                for (int r = 0; r <= 1; r++) {

                    for (int dn = 0; dn <= 1; dn++) {

                        for (int l = 0; l <= 1; l++ ) {

                            qTable.get(cl).get(up).get(r).get(dn).set(l, this.getInitActionValues() );
                        
                        }
                    }
                }
            }
        }
    }

    protected ArrayList<Double> getInitActionValues() {
        ArrayList<Double> initVals = new ArrayList<Double>();
        double mean = 0.5, std = 0.05;
        Random rdm = new Random();
        for (int i = 0; i < super.numActions; i++) {
            initVals.add( mean + std * rdm.nextGaussian() );
        }
        return initVals;
    }

    protected void setQValue( ArrayList<Domain> obs, Integer idAction, Double val ) {
        // cl, up, right, down, left, action
        qTable.get( obs.get(0).intValue() ).get( obs.get(1).intValue() ).get( obs.get(2).intValue() ).get(
            obs.get(3).intValue() ).get( obs.get(4).intValue() ).set( idAction, val );
    }

    protected void setQValues( ArrayList<Domain> obs, ArrayList<Double> vals ) {
        // cl, up, right, down, left, action
        qTable.get( obs.get(0).intValue() ).get( obs.get(1).intValue() ).get( obs.get(2).intValue() ).get(
                obs.get(3).intValue() ).set( obs.get(4).intValue(), vals );
    }

    @Override
    protected Double getValue(ArrayList<Domain> obs, Domain idAction) {
        return qTable.get( obs.get(0).intValue() ).get( obs.get(1).intValue() ).get( obs.get(2).intValue() ).get(
            obs.get(3).intValue() ).get( obs.get(4).intValue() ).get( idAction.intValue() );
    }

    @Override
    protected ArrayList<Double> getValues(ArrayList<Domain> obs) {
        if (obs != null) {
            return qTable.get( obs.get(0).intValue() ).get( obs.get(1).intValue() ).get( obs.get(2).intValue() ).get(
            obs.get(3).intValue() ).get( obs.get(4).intValue() );
        }
        return this.getInitActionValues();
    }

    @Override
    protected void update (ArrayList<Domain> state, ArrayList<Domain> newState, Domain action, Domain reward) {

        Integer idAction = action.intValue();
        Double maxFutureQ = super.getBestValue(newState);
        ArrayList<Double> qValues = this.getValues(state);
        Double qVal = qValues.get(idAction);

        qVal += super.ALPHA * (reward.doubleValue() + super.GAMMA * maxFutureQ - qVal);
        qValues.set(idAction, qVal);

        this.setQValues(state, qValues);
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
                    (ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Double>>>>>>)
                            objectInputStream.readObject();

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
