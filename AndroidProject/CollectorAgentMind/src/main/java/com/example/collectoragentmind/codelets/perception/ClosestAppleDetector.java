package com.example.collectoragentmind.codelets.perception;

import com.example.collectoragentmind.CollectorAgentMind;
import com.example.collectoragentmind.util.Apple;
import com.example.collectoragentmind.util.Vector2;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

public class ClosestAppleDetector extends Codelet {

    /*
        Scans the list of known apples and updates the
        closestAppleMO accordingly. Stores null in the
        MO if there are no known apples
    */

    private MemoryObject touchMO;
    private MemoryObject knownApplesMO;
    private MemoryObject closestAppleMO;
    private MemoryObject positionMO;

    @Override
    public void accessMemoryObjects() {
        this.knownApplesMO = (MemoryObject) this.getInput("KNOWN_APPLES");
        this.positionMO = (MemoryObject) this.getInput("POSITION");
        this.closestAppleMO = (MemoryObject) this.getOutput("CLOSEST_APPLE");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        ArrayList<Apple> knownApples = (ArrayList<Apple>) knownApplesMO.getI();

        if (knownApples.size() > 0) {
            Vector2 position = (Vector2) positionMO.getI();

            if (position != null) {
                Apple closestApple = knownApples.get(0);
                double minDistance = position.distanceTo(closestApple.position);

                for (int i = 1; i < knownApples.size(); i++) {
                    Apple newApple = knownApples.get(i);
                    double newDistance = position.distanceTo(newApple.position);

                    if (newDistance < minDistance) {
                        closestApple = newApple;
                        minDistance = newDistance;
                    }
                }

                closestAppleMO.setI(closestApple);
            }
        } else {
            closestAppleMO.setI(null);
        }

        CollectorAgentMind.getInstance().updateValue(closestAppleMO.getI().toString(), "CLOSEST_APPLE");
    }
}
