package com.example.collectoragentmind.codelets.motor;

import com.example.collectoragentmind.CollectorAgentMind;
import com.example.collectoragentmind.util.Apple;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

public class HandActionCodelet extends Codelet {

    /*
        Sends commands from the handsMO to Godot as signals
    */

    private CollectorAgentMind plugin;

    private MemoryObject handsMO;

    public HandActionCodelet() {
        plugin = CollectorAgentMind.getInstance();
    }

    @Override
    public void accessMemoryObjects() {
        handsMO = (MemoryObject) getInput("HANDS");
    }

    @Override
    public void calculateActivation() {

    }


    @Override
    public void proc() {
        ArrayList<Apple> applesInHand = (ArrayList<Apple>) handsMO.getI();

        int totalApples = applesInHand.size();

        if (totalApples > 0) {

            for (int i = 0; i < totalApples; i++) {
                Apple newApple = applesInHand.get(i);

                plugin.eatApple(newApple.name);
            }
        }

        CollectorAgentMind.getInstance().updateValue(handsMO.getI().toString(), "HANDS");
    }
}
