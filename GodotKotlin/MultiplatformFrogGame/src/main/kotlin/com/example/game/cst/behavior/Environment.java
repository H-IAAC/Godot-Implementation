package com.example.game.cst.behavior;
import br.unicamp.cst.core.entities.MemoryObject;

import java.util.ArrayList;


public abstract class Environment {
    protected MemoryObject stateMO;
    protected Domain[] actionSpace;

    public Environment(MemoryObject stateMO, Domain[] actionSpace) {
        this.stateMO = stateMO;
        this.actionSpace = actionSpace;
    }

    public Object getObservationSpace() {
        return ((ArrayList<Domain>) this.stateMO.getI());
    }

    public Domain[] getActionSpace() {
        return this.actionSpace;
    }

    // extract information of Memory Objects to update enviroment.
    protected abstract void extractMemoryObjects();

    // returns the new observation space at the end of the function.
    public abstract ArrayList<Domain> reset();

    // returns {observationSpace : ArrayList<Domain>, reward : Domain, done : boolean, info : String}
    public abstract ArrayList step(Domain action);

    // depending of your problem, you might want to display your environment.
    public abstract void render();
}
