package com.example.collectoragentmind.codelets.sensors;

import com.example.collectoragentmind.CollectorAgentMind;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

public class InnerSense extends Codelet {

    /*
        Requests the agent's position every proc. The body
        should respond to this request by updating the
        positionMO through the plugin's interface
    */

    private MemoryObject positionMO;
    private CollectorAgentMind plugin;

    public InnerSense() {
        plugin = CollectorAgentMind.getInstance();
    }

    @Override
    public void accessMemoryObjects() {

    }

    public void proc() {
        plugin.emitRequest("request_agent_position");
    }

    @Override
    public void calculateActivation() {

    }
}
