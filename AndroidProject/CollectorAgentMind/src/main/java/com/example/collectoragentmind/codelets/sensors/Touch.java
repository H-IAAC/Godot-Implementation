package com.example.collectoragentmind.codelets.sensors;

import com.example.collectoragentmind.CollectorAgentMind;

import br.unicamp.cst.core.entities.Codelet;

public class Touch extends Codelet {

    /*
        Requests the apples in reach every proc. The body
        should respond to this request by updating the
        touchMO through the plugin's interface
    */

    private CollectorAgentMind plugin;

    public Touch() {
        plugin = CollectorAgentMind.getInstance();
    }

    @Override
    public void accessMemoryObjects() {

    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        plugin.emitRequest("request_apples_in_reach");
    }
}
