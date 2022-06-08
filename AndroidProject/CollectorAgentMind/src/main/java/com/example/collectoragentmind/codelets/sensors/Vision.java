package com.example.collectoragentmind.codelets.sensors;

import com.example.collectoragentmind.CollectorAgentMind;

import java.util.List;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;

public class Vision extends Codelet {

    /*
        Requests the apples in sight every proc. The body
        should respond to this request by updating the
        visionMO through the plugin's interface
    */

    private CollectorAgentMind plugin;

    public Vision() {
        plugin = CollectorAgentMind.getInstance();
    }

    @Override
    public void accessMemoryObjects() {
    }

    @Override
    public void proc() {
        plugin.emitRequest("request_apples_in_sight");
    }

    @Override
    public void calculateActivation() {

    }
}