package com.example.game.cst.behavior;

import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.util.viewer.ObjectTreeNode;
import com.example.game.FrogMindCommunicator;

import java.util.ArrayList;

public class FrogEnv extends  Environment {
    FrogMindCommunicator communicator;

    public FrogEnv(MemoryObject stateMO, Domain[] actionSpace, FrogMindCommunicator communicator) {
        super(stateMO, actionSpace);
        this.actionSpace = actionSpace;
        this.communicator = communicator;
    }

    @Override
    protected void extractMemoryObjects() {
        ArrayList<Domain> state = ((ArrayList<Domain>) super.stateMO.getI());
    }

    @Override
    public ArrayList<Domain> reset() {
        // return communicator.reset();
        return new ArrayList<Domain>();
    }

    @Override
    public ArrayList<Domain> step(Domain action) {
        // return communicator.step(action);
        return new ArrayList<Domain>();
    }

    @Override
    public void render() {
        communicator.render();
    }
}
