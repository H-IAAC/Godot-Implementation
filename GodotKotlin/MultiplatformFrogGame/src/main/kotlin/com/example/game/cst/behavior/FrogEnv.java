package com.example.game.cst.behavior;

/* 1. Needs to implement reward policy */

import br.unicamp.cst.core.entities.MemoryObject;
import com.example.game.FrogMindCommunicator;

import java.util.ArrayList;

public class FrogEnv extends  Environment {
    FrogMindCommunicator communicator;

    public FrogEnv(MemoryObject stateMO, Domain[] actionSpace,
                   FrogMindCommunicator communicator) {
        super(stateMO, actionSpace);
        this.actionSpace = actionSpace;
        this.communicator = communicator;
    }

    /*
     * create custom metric of reward
     */
    private Domain getReward() {
        ArrayList<Domain> state = ((ArrayList<Domain>) super.stateMO.getI());
        Domain reward = new Domain(0.0);
        // ... add if and else considering position of things
        return reward;
    }

    /*
    * calls a step in the environment.
    * Return ArrayList { state: ArrayList<Domain>, reward: Domain,
                          done: Boolean, info: String }
    * */
    @Override
    public ArrayList step(Domain action) {
        boolean isDone = communicator.step(action.intValue());

        ArrayList step = new ArrayList();
        step.add(this.stateMO.getI());
        step.add(this.getReward());
        step.add(isDone);
        step.add("");

        return step;
    }

    @Override
    public void render() {
        communicator.render();
    }

    // it is probably unnecessary
    @Override
    protected void extractMemoryObjects() {
        ArrayList<Domain> state = ((ArrayList<Domain>) super.stateMO.getI());
    }

    @Override
    public ArrayList<Domain> reset() {
        communicator.reset();
        return (ArrayList<Domain>) this.stateMO.getI();
    }
}

