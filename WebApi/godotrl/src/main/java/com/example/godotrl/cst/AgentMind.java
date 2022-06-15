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
    MemoryObject updateMO = null;
    MemoryObject motorMO = null;

    public void initialize() {
        /*
            INITIALIZE MEMORY OBJECTS
        */

        // Sensor
        positionMO = createMemoryObject("POSITION", new Vector2(0, 0));
        visionMO = createMemoryObject("VISION", new ArrayList<Vector2>());

        // Perception
        MemoryObject knownCarsMO = createMemoryObject("KNOWN_CARS", new ArrayList<Vector2>());
        MemoryObject closestCarsMO = createMemoryObject("CLOSEST_CARS", new ArrayList<Vector2>());
        MemoryObject stateMO = createMemoryObject("STATE", null);
        MemoryObject lastStateMO = createMemoryObject("LAST_STATE", null);

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

        String pathToSaveLearning = "/home/ianloron00/IC/Godot-Implementation/WebApi/godotrl/callback/";
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
        LearnerCodelet learnerCodelet = new LearnerCodelet(
                0.9999, 0.05,
                1L, true, true,
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

    public Action getActionData() {
        if (motorMO != null) {
            ((Updater) updateMO.getI()).updateMotor();

            return (Action) motorMO.getI();
        }
        return Action.INVALID;
    }
}
