package com.example.csttest.cst;

import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;

public class AgentMind extends Mind {
    private MemoryObject testMO;

    public void initialize() {
        TestCodelet testCodelet = new TestCodelet();
        testMO = createMemoryObject("TEST", 1);
        testCodelet.addOutput(testMO);

        start();
    }

    public int getValue() {
        return (int) testMO.getI();
    }
}
