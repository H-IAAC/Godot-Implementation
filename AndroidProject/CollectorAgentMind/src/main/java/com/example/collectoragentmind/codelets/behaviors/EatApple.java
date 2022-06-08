package com.example.collectoragentmind.codelets.behaviors;

import com.example.collectoragentmind.CollectorAgentMind;
import com.example.collectoragentmind.util.Apple;
import com.example.collectoragentmind.util.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;

public class EatApple extends Codelet {

    /*
        For all apples in reach, as per the touchMO,
        adds its name in the handsMO
    */

    private MemoryObject touchMO;
    private MemoryObject handsMO;

    @Override
    public void accessMemoryObjects() {
        touchMO = (MemoryObject) this.getInput("TOUCH");
        handsMO = (MemoryObject) this.getOutput("HANDS");
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

    private int getApplePositionByName(ArrayList<Apple> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void proc() {
        ArrayList<Apple> touchedApples = (ArrayList<Apple>) touchMO.getI();

        if (touchedApples.size() > 0) {
            ArrayList<Apple> applesInHand = (ArrayList<Apple>) handsMO.getI();

            for (int i = 0; i < touchedApples.size(); i++) {
                Apple newApple = touchedApples.get(i);

                if (!appleInList(applesInHand, newApple)) {
                    applesInHand.add(newApple);
                }
            }

            handsMO.setI(applesInHand);
        } else {
            handsMO.setI(new ArrayList<Apple>());
        }
    }
}
