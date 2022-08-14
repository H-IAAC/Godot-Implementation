package com.example.godotrl.cst;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;
import com.example.godotrl.cst.behavior.*;
import com.example.godotrl.cst.perception.CarDetection;
import com.example.godotrl.cst.perception.CarUpdater;
import com.example.godotrl.cst.perception.CloseCars;
import com.example.godotrl.cst.perception.StateManager;
import com.example.godotrl.util.*;

import java.util.ArrayList;

public class AgentMind extends Mind {
    MemoryObject positionMO = null;
    MemoryObject visionMO = null;

    MemoryObject knownCarsMO = null;
    MemoryObject closestCarsMO = null;
    MemoryObject stateMO = null;
    MemoryObject lastStateMO = null;

    MemoryObject updateMO = null;
    MemoryObject motorMO = null;

    LearnerCodelet learnerCodelet = null;
    CarUpdater carUpdater = null;

    public void resetMOs(MapData mapData) {
        // Sensor
        positionMO.setI(new Vector2(0, 0));
        visionMO.setI(new ArrayList<Vector2>());

        // Perception
        knownCarsMO.setI(new ArrayList<Vector2>());
        closestCarsMO.setI(new ArrayList<Vector2>());
        stateMO.setI(null);
        lastStateMO.setI(null);

        // Behavior
        updateMO.setI(new Updater());

        // Motor
        motorMO.setI(Action.INVALID);

        carUpdater.setAlive(true);
        carUpdater.setMapData(mapData);
        learnerCodelet.resetWinState();
    }

    public void initialize(MapData mapData) {
        /*
            INITIALIZE MEMORY OBJECTS
        */

        // Sensor
        positionMO = createMemoryObject("POSITION", new Vector2(0, 0));
        visionMO = createMemoryObject("VISION", new ArrayList<Vector2>());

        // Perception
        knownCarsMO = createMemoryObject("KNOWN_CARS", new ArrayList<Vector2>());
        closestCarsMO = createMemoryObject("CLOSEST_CARS", new ArrayList<Vector2>());
        stateMO = createMemoryObject("STATE", null);
        lastStateMO = createMemoryObject("LAST_STATE", null);

        // Behavior
        updateMO = createMemoryObject("UPDATE", new Updater());

        // Motor
        motorMO = createMemoryObject("MOTOR", Action.INVALID);

        /*
            INITIALIZE CODELETS
        */

        // Perception
        CarDetection carDetection = new CarDetection();
        carDetection.addInput(visionMO);
        carDetection.addOutput(knownCarsMO);
        carDetection.addOutput(updateMO);
        insertCodelet(carDetection, "PERCEPTION");

        carUpdater = new CarUpdater(mapData);
        carUpdater.addInput(positionMO);
        carUpdater.addOutput(knownCarsMO);
        carUpdater.addOutput(updateMO);
        insertCodelet(carUpdater, "PERCEPTION");

        CloseCars closeCars = new CloseCars();
        closeCars.addInput(knownCarsMO);
        closeCars.addInput(positionMO);
        closeCars.addOutput(closestCarsMO);
        closeCars.addOutput(updateMO);
        insertCodelet(closeCars, "PERCEPTION");

        StateManager stateManager = new StateManager();
        stateManager.addInput(positionMO);
        stateManager.addInput(closestCarsMO);
        stateManager.addOutput(stateMO);
        stateManager.addOutput(lastStateMO);
        stateManager.addOutput(updateMO);
        insertCodelet(stateManager, "PERCEPTION");

        // Behavior
        String pathToSaveLearning = "/home/ic-unicamp/IC/Godot-Implementation/WebApi/godotrl/callback/";
        // String pathToSaveLearning = "/home/ianloron00/IC/Godot-Implementation/WebApi/godotrl/callback/";
        // String pathToSaveLearning = "/c/Users/morai/OneDrive/Documentos/Git/Godot-Implementation/callback/";
        String rlFile = "simple-0908-lfa-weights-new-rw-new-extr.txt"; // CHANGE TO .txt!!
        String rewardFile = "testing-0908-2000-eps-new-rw-new-extr.csv";
        Integer nMaxSteps = 50;
        Double epsilonInitial = 0.99;
        Double epsilonFinal = 0.01;
        // percenteage of num episides that the agent should explore.
        Double explorationPeriod = 0.5;
        Long checkPointEachNEpisodes = 500L;
        Double learningRate = 0.02;
        Long numEpisodes = 50L;
        Double xLen = 5.0;
        Double yLen = 5.0;
        Boolean isTraining = false;
        Boolean isTabular = false;

        /*
         * Double alpha, Double gamma, Integer numActions, String pathToSaveLearning, FroggerFE fe
         * */
        FroggerLFA rl = new FroggerLFA( learningRate, 0.98, 5, pathToSaveLearning, new FroggerSimpleFE(xLen, yLen, nMaxSteps), xLen, yLen ) ;
//        Tabular rl = new Tabular( learningRate, 0.98, 5, pathToSaveLearning ) ;

        /*
              Double epsilonInitial, Double epsilonFinal,
              Long numEpisodes, Boolean isTraining, Boolean isTabular,
              ValueBasedRL learning, Domain<Double>[] actionSpace,
              String localPathToCheckpoint, String learningFileName,
              String cumRewardFileName, Long checkpointEachNEpisodes,
              Integer nMaxSteps
              * */
        FrogEnv env = new FrogEnv(stateMO, new Domain[] {new Domain(0), new Domain(0), new Domain(4)}, yLen);
        learnerCodelet = new LearnerCodelet(
                epsilonInitial, epsilonFinal, explorationPeriod,
                numEpisodes, isTraining, isTabular,
                rl, env,
                pathToSaveLearning, rlFile,
                rewardFile, checkPointEachNEpisodes, nMaxSteps
        );
        learnerCodelet.addOutput(updateMO);
        learnerCodelet.addOutput(motorMO);
        learnerCodelet.addInput(stateMO);
        learnerCodelet.addInput(lastStateMO);
        insertCodelet(learnerCodelet, "BEHAVIOR");

        for (Codelet c : this.getCodeRack().getAllCodelets())
            c.setTimeStep(10);

        start();
    }

    public void updateSensor(Vector2 pos, ArrayList<Vector2> cars) {
        if (positionMO != null && visionMO != null) {
            if (((Updater) updateMO.getI()).canUpdateSensor()) {
                positionMO.setI(pos);
                visionMO.setI(cars);
                ((Updater) updateMO.getI()).updateSensor();
            }
        }
    }

    public boolean canGetActionData() {
        if (motorMO != null) {
            return ((Updater) updateMO.getI()).updateMotor();
        }
        return false;
    }

    public Action getActionData() {
        return (Action) motorMO.getI();
    }

    public String canEndMessage() {
        if (learnerCodelet.canEnd()) {
            return "DONE";
        }
        return "NOT_DONE";
    }

    public void win() {
        if (learnerCodelet != null) {
            learnerCodelet.win();

            carUpdater.setAlive(false);
            ((Updater) updateMO.getI()).end();
        }
    }

    public void lose() {
        if (learnerCodelet != null) {
            learnerCodelet.lose();

            carUpdater.setAlive(false);
            ((Updater) updateMO.getI()).end();
        }
    }
}
