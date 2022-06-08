package com.example.collectoragentmind.codelets.motor;

import com.example.collectoragentmind.CollectorAgentMind;
import com.example.collectoragentmind.util.Vector2;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

public class LegActionCodelet extends Codelet {

    /*
        Sends commands from the legsMO to Godot as signals
    */

    private CollectorAgentMind plugin;

    private MemoryObject legsMO;

    public LegActionCodelet() {
        plugin = CollectorAgentMind.getInstance();
    }

    @Override
    public void accessMemoryObjects() {
        legsMO = (MemoryObject) getInput("LEGS");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        Vector2 legAimPos = (Vector2) legsMO.getI();

        if (legAimPos == null) {
            plugin.forage();
        } else {
            plugin.goTo(legAimPos.x, legAimPos.y);
        }

        CollectorAgentMind.getInstance().updateValue(legsMO.getI().toString(), "LEGS");
    }
}
