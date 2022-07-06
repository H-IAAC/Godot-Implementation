package com.example.godotrl.cst.behavior;

import com.example.godotrl.util.Action;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Vector2;

import java.util.*;

public class FroggerLFA extends LFA {

    protected Double yLim;
    protected Double xLim;

    public FroggerLFA(Double alpha, Double gamma, Integer numActions, String pathToSaveLearning, FroggerFE fe,
                      Double xLen, Double yLen ) {
        super(alpha, gamma, numActions, pathToSaveLearning, fe);
        this.xLim = xLen;
        this.yLim = yLen;
        initWeights();
    }

    public void update(State state, State newState, Action action, Domain reward, Boolean isDone, Boolean won) {
        LinkedHashMap<String, Double> gradient = ((FroggerFE) this.extractor).getFeatures(state, action, isDone, won);

        Double new_q_val;
        if (isDone)
            new_q_val = 0.0;
        else
            new_q_val = this.getBestValue(newState);

        Double q_val = this.getValue(state, action, isDone, won);

        Double target = reward.doubleValue() + super.GAMMA * new_q_val;

        Set<String> name_features = gradient.keySet();
        for(String f : name_features) {
            Double w = this.weights.get(f) + super.ALPHA * (target - q_val) * gradient.get(f);
            this.weights.put(f, w);
        }
        return;
    }

    public LinkedHashMap<String, Double> getWeights() {
        return this.weights;
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


    protected ArrayList<Double> getValues(State state) {
        ArrayList<Double> futureVals = new ArrayList<>();

        Action[] validActions = new Action[4];
        System.arraycopy(Action.values(), 0, validActions, 0, 4);
        for (Action _action : validActions ) {
            // int predictStep = isTheEnd(state, _action);
            // Boolean done = predictStep > 0;
            // Boolean won = done && predictStep == 1;
            futureVals.add( this.getValue(state, _action, false, false) );
        }

        return futureVals;
    }

    private Double getBestValue(State state) {
        return Collections.max( this.getValues( state ) );
    }

    private int isTheEnd(State state, Action action ) {
        Double x = state.getPosition().getX();
        Double y = state.getPosition().getY();
        // won
        if ( y == 1.0 && action == Action.UP )
            return 1;

        switch (action) {
            case UP:
                y = Math.max( 0.0, y-1 );
                break;
            case RIGHT:
                x = Math.min( xLim, x+1 );
                break;
            case DOWN:
                y = Math.min( y+1, yLim-1 );
                break;
            case LEFT:
                x = Math.max( 0.0, x-1 );
                break;
        }
        for (Vector2 car : state.getClosestCars() ) {
            if ( car.getY() == y && ( car.getX() == x || car.getX() == x-1 ) )
                return 2;
        }

        return 0;
    }

    private void initWeights() {
        ArrayList<Vector2> cars = new ArrayList<>();
        cars.add( new Vector2(-999, -999) );
        cars.add( new Vector2(-999, -999) );
        cars.add( new Vector2(-999, -999) );
        cars.add( new Vector2(-999, -999) );
        cars.add( new Vector2(-999, -999) );
        State st = new State( new Vector2(0, 0), cars );
        Action a = Action.UP;
        LinkedHashMap<String, Double> grad = ((FroggerFE) extractor).getFeatures(st, a, false, false);

        double mean = 0.5, std = 0.05;
        Random rdm = new Random();
        Set<String> features = grad.keySet();
        for (String f : features) {
            weights.put(f, mean + std * rdm.nextGaussian() );
        }
    }

    protected int getIdBestValue( State state ) {
        Double max = Double.MIN_VALUE;
        int id = 0;
        ArrayList<Double> vals = getValues( state );
        for ( int i = 0; i < vals.size(); i++ ) {
            if ( vals.get(i) > max) {
                max = vals.get(i);
                id = i;
            }
        }
        return id;
    }

    protected Domain epsilonGreedyPolicy(Double epsilon, State state) {
        if (Math.random() < epsilon)
            return new Domain( (int) Math.floor( ((Math.random() - 0.1) * (Math.max(0, this.numActions - 1) ) ) ) );
        else {
            return new Domain( getIdBestValue(state) );
        }
    }
}
