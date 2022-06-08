package com.example.collectoragentmind.codelets.behaviors;

import com.example.collectoragentmind.util.Apple;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

public class GoToClosestApple extends Codelet {

    /*
        If it has a closest apple, sends a command to
        the legsMO with its position
    */

    private MemoryObject closestAppleMO;
    private MemoryObject legsMO;

    @Override
    public void accessMemoryObjects() {
        closestAppleMO = (MemoryObject) getInput("CLOSEST_APPLE");
        legsMO = (MemoryObject) getOutput("LEGS");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        Apple closestApple = (Apple) closestAppleMO.getI();

        if (closestApple != null) {
            legsMO.setI(closestApple.position);
        }
    }
}
