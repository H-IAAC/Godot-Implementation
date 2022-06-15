package com.example.godotrl.cst;

import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;
import com.example.godotrl.cst.behavior.Domain;
import com.example.godotrl.cst.behavior.LearnerCodelet;
import com.example.godotrl.cst.behavior.Tabular;
import com.example.godotrl.cst.perception.CarDetection;
import com.example.godotrl.cst.perception.CarUpdater;
import com.example.godotrl.cst.perception.CloseCars;
import com.example.godotrl.cst.perception.StateManager;
import com.example.godotrl.util.Action;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Updater;
import com.example.godotrl.util.Vector2;

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

    public void resetMOs() {
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

        learnerCodelet.resetWinState();
    }

    public void initialize() {
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

        CarUpdater carUpdater = new CarUpdater();
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

        //String pathToSaveLearning = "/home/ianloron00/IC/Godot-Implementation/WebApi/godotrl/callback/";
        String pathToSaveLearning = ".."+System.getProperty("file.separator")+".."+System.getProperty("file.separator")+".."+System.getProperty("file.separator")+".."+System.getProperty("file.separator")+".."+System.getProperty("file.separator")+".."+System.getProperty("file.separator")+".."+System.getProperty("file.separator")+".."+System.getProperty("file.separator")+"callback/";
        String qFile = "q-table.csv";
        String rewardFile = "rewards-qlearning.csv";
        Long checkPointEachNEpisodes = 1000L;

        /*
         * Double alpha, Double gamma, Integer numActions, String pathToSaveLearning
         * */
        Tabular rl = new Tabular(0.99, 0.95, 5, pathToSaveLearning);

        /*
        *                 Double epsilonInitial, Double epsilonFinal,
                          Long numEpisodes, Boolean isTraining, Boolean isTabular,
                          ValueBasedRL learning, Domain<Double>[] actionSpace,
                          String localPathToCheckpoint, String learningFileName,
                          String cumRewardFileName, Long checkpointEachNEpisodes
                          * */
        learnerCodelet = new LearnerCodelet(
                0.9999, 0.05,
                3L, true, true,
                rl, new Domain[] {new Domain(0), new Domain(0), new Domain(4)},
                pathToSaveLearning, qFile,
                rewardFile, checkPointEachNEpisodes
        );
        learnerCodelet.addOutput(updateMO);
        learnerCodelet.addOutput(motorMO);
        learnerCodelet.addInput(stateMO);
        learnerCodelet.addInput(lastStateMO);
        insertCodelet(learnerCodelet, "BEHAVIOR");

        start();
    }

    public void updateSensor(Vector2 pos, ArrayList<Vector2> cars) {
        if (positionMO != null && visionMO != null) {
            if (((Updater) updateMO.getI()).updateSensor()) {
                positionMO.setI(pos);
                visionMO.setI(cars);
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
        }
    }

    public void lose() {
        if (learnerCodelet != null) {
            learnerCodelet.lose();
        }
    }
}
