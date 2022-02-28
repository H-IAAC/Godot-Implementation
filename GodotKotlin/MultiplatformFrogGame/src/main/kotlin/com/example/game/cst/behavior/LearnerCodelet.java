/*
* TODO
*   save Q-table
*   isTraining?
*   isLFA? Define Features Extractor;
*   render?
*
*   (FrogEnv)
*   function step(Domain idAction):
*   return ArrayList { state: ArrayList<Domain>, reward: Domain,
*                      done: Boolean, info: String }
*
*   (FrogMindCommunicator)
*   function step: return { done: Boolean }
*
*   (LearnerCodelet)
*   function proc():
*   should it run as a step or as a 'main'?
* */

package com.example.game.cst.behavior;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import com.example.game.FrogMindCommunicator;

import java.util.ArrayList;

public class LearnerCodelet extends Codelet {
    MemoryObject stateMO;
    FrogMindCommunicator communicator;
    Boolean isTraining;
    Boolean isTabular;

    LearnerCodelet(MemoryObject stateMO, FrogMindCommunicator communicator) {
        this.stateMO = stateMO;
        this.communicator = communicator;
    }


    @Override
    public void proc() {
        Domain actionSpace[] =  {new Domain(0), new Domain(0), new Domain(4)};
        FrogEnv env = new FrogEnv(stateMO, actionSpace, communicator);

        int numEpisodes = 20000;
        Double epsilon = 0.99;
        Double epsilonFinal = 0.05;
        Double epsilonDecay = epsilon/(numEpisodes);
        Double gamma = 0.95;
        Double alpha = 0.1;

        Tabular qLearning = new Tabular(alpha, gamma, actionSpace[2].intValue() - actionSpace[1].intValue());


        for (int e = 0; e < numEpisodes; e++) {
            ArrayList<Domain> obsSpace = env.reset();
            Boolean done = false;
            Double episodeReward = 0.0;

            while(!done) {
                Domain idAction = qLearning.epsilonGreedyPolicy(epsilon, obsSpace);
                ArrayList step = env.step(idAction);
                ArrayList<Domain> newObs = (ArrayList<Domain>) step.get(0);
                Domain reward = (Domain) step.get(1);
                done = (Boolean) step.get(2);
                String info = (String) step.get(3);

                episodeReward += reward.doubleValue();

                // if render {...}

                qLearning.update(obsSpace, newObs, idAction, reward);
                obsSpace = newObs;

                epsilon = Math.max(epsilon - epsilonDecay, epsilonFinal);
            }
        }
    }

    @Override
    public void accessMemoryObjects() {
        return;
    }

    @Override
    public void calculateActivation() {
        return;
    }
}
