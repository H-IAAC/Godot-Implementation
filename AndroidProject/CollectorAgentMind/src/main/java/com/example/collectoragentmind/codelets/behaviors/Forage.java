package com.example.collectoragentmind.codelets.behaviors;

import com.example.collectoragentmind.util.Apple;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

public class Forage extends Codelet {

    /*
        If there are no known apples, makes the legsMO
        null
    */

    private MemoryObject knownApplesMO;
    private MemoryObject legsMO;

    @Override
    public void accessMemoryObjects() {
        knownApplesMO = (MemoryObject) this.getInput("KNOWN_APPLES");
        legsMO = (MemoryObject) this.getOutput("LEGS");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        ArrayList<Apple> knownApples = (ArrayList<Apple>) knownApplesMO.getI();

        if (knownApples.size() == 0) {
            legsMO.setI(null);
        }
    }
}
