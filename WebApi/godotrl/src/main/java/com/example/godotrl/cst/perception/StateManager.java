package com.example.godotrl.cst.perception;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import com.example.godotrl.util.State;
import com.example.godotrl.util.Updater;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;

public class StateManager extends Codelet {
    MemoryObject positionMO = null;
    MemoryObject closestCarsMO = null;
    MemoryObject stateMO = null;
    MemoryObject lastStateMO = null;
    MemoryObject updateMO = null;

    @Override
    public void accessMemoryObjects() {
        positionMO = (MemoryObject) getInput("POSITION");
        closestCarsMO = (MemoryObject) getInput("CLOSEST_CARS");
        stateMO = (MemoryObject) getOutput("STATE");
        lastStateMO = (MemoryObject) getOutput("LAST_STATE");
        updateMO = (MemoryObject) getOutput("UPDATE");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        if (((Updater) updateMO.getI()).updateState()) {
            lastStateMO.setI(stateMO.getI());
            stateMO.setI(new State((Vector2) positionMO.getI(), (ArrayList<Vector2>) closestCarsMO.getI()));
        }
    }
}
