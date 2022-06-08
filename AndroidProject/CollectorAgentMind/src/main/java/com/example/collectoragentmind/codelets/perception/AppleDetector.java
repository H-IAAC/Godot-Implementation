package com.example.collectoragentmind.codelets.perception;

import com.example.collectoragentmind.CollectorAgentMind;
import com.example.collectoragentmind.util.Apple;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

public class AppleDetector extends Codelet {

    /*
        Scans the visionMO and includes all unknown apples
        in the knownApplesMO
    */

    private MemoryObject visionMO;
    private MemoryObject knownApplesMO;

    @Override
    public void accessMemoryObjects() {
        synchronized(this) {
            this.visionMO = (MemoryObject) this.getInput("VISION");
        }
        this.knownApplesMO = (MemoryObject) this.getOutput("KNOWN_APPLES");
    }

    @Override
    public void calculateActivation() {

    }

    private boolean appleInList(ArrayList<Apple> list, Apple apple) {
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).name.equals(apple.name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void proc() {
        ArrayList<Apple> applesInSight = (ArrayList<Apple>) visionMO.getI();
        ArrayList<Apple> knownApples = (ArrayList<Apple>) knownApplesMO.getI();

        for (int i = 0; i < applesInSight.size(); i++) {
            if (!appleInList(knownApples, applesInSight.get(i))) {
                knownApples.add(applesInSight.get(i));
            }
        }

        knownApplesMO.setI(knownApples);

        CollectorAgentMind.getInstance().updateValue(knownApplesMO.getI().toString(), "KNOWN_APPLES");
    }
}
