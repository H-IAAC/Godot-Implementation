package com.example.cstgodottest.cst;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

public class TestCodelet extends Codelet {
    private int value = 0;
    private int addTo = 1;

    private MemoryObject testMO;

    @Override
    public void accessMemoryObjects() {
        testMO = (MemoryObject) getOutput("TEST");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        value += addTo;

        if (value >= 100){
            addTo = -1;
        } else if (value <= 0) {
            addTo = 1;
        }

        testMO.setI(value);
    }
}

