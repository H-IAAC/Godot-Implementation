package com.example.cstgodottest.cst;

import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;

public class AgentMind extends Mind {
    private MemoryObject testMO;
    private String name = "";

    public void initialize(String newName) {
        TestCodelet testCodelet = new TestCodelet();
        testMO = createMemoryObject("TEST", 1);
        testCodelet.addOutput(testMO);
        insertCodelet(testCodelet);

        name = newName;

        start();
    }

    public String getValue() {
        return name + " - " + testMO.getI();
    }
}