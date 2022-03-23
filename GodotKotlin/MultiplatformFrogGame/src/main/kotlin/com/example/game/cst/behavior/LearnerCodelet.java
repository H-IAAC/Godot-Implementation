package com.example.game.cst.behavior;

/*
* StateManager: how to access older states? How to call it?
    (obs: it was not necessary to create StateManager... just copy the state before the step.)

* stateMO: remember to apply getI() before communicating to the learning part! (done)
* Q-table / LFA Weights: how to save it? (done)
    Serialize it. Notice that HashMap already implements Serializable.io. Follow to tutorial:
    https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/

* What is the real purpose of the communicator?
 */


import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import com.example.game.FrogMindCommunicator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class LearnerCodelet extends Codelet {
    MemoryObject stateMO;
    FrogMindCommunicator communicator;
    Boolean isTraining;
    Boolean isTabular;
    Double alpha;
    Double gamma;
    Double epsilon;
    Double epsilonInitial;
    Double epsilonFinal;
    Double epsilonDecay;
    Tabular qLearning;
    LFA lfa;
    Long numEpisodes;
    // actionSpace[3] = {isRealDomain(0 or 1), minValue, maxValue-1}
    Domain<Double>[] actionSpace;
    FrogEnv env;
    String pathToSaveLearning;
    // e.g. "q_learning.txt", "lfa_weights.txt"
    String learningFileName;

    Boolean episodeIsDone = false;
    Domain reward = new Domain(0.0);
    Long currEpisode = 0L;
    Integer currStep = 0;

    LearnerCodelet(MemoryObject stateMO, FrogMindCommunicator communicator,
                   Double alpha, Double gamma, Double epsilonInitial, Double epsilonFinal,
                   Long numEpisodes, Boolean isTraining, Boolean isTabular,
                   ValueBasedRL learning, Domain[] actionSpace,
                   String pathToSaveLearning, String learningFileName) {
        this.stateMO = stateMO;
        this.communicator = communicator;
        this.alpha = alpha;
        this.gamma = gamma;
        this.epsilon = epsilonInitial;
        this.epsilonInitial = epsilonInitial;
        this.epsilonFinal = epsilonFinal;
        this.numEpisodes = numEpisodes;
        this.epsilonDecay = (epsilonInitial - epsilonFinal) / numEpisodes;
        this.isTraining = isTraining;
        this.isTabular = isTabular;
        this.actionSpace = actionSpace;
        this.env = new FrogEnv(stateMO, actionSpace, communicator);
        this.pathToSaveLearning = pathToSaveLearning;
        this.learningFileName = learningFileName;
        setLearningType();
    }

    private void setLearningType() {
        if (this.isTabular) {
            this.qLearning =
                    new Tabular(this.alpha, this.gamma,
                            this.actionSpace[2].intValue(),
                            this.pathToSaveLearning);
        } else {
            FeaturesExtractor fe = new FeaturesExtractor();
            this.lfa =
                    new LFA(this.alpha, this.gamma,
                            this.actionSpace[2].intValue(),
                            this.pathToSaveLearning, fe);
        }
    }

    @Override
    public void proc() {
        if (this.currEpisode < this.numEpisodes) {
            if (this.currStep == 0 && this.currEpisode == 0) {
                if (Files.exists(
                        Path.of(this.pathToSaveLearning + this.learningFileName))) {
                    if (this.isTabular)
                        this.qLearning.deserializeLearning(this.learningFileName);
                    else
                        this.lfa.deserializeLearning(this.learningFileName);
                }
            }
            if(!this.episodeIsDone) {
                Domain idAction;
                if (isTabular)
                    idAction =
                            qLearning.epsilonGreedyPolicy(this.epsilon, (ArrayList<Domain>) this.stateMO.getI());
                else
                    idAction =
                            lfa.epsilonGreedyPolicy(this.epsilon, (ArrayList<Domain>) this.stateMO.getI());

                ArrayList<Domain> lastState =
                        new ArrayList<Domain>(
                                (ArrayList<Domain>) this.stateMO.getI());
                ArrayList step = env.step(idAction);
                /* notice that it is not necessary to get state */
                Double currReward = ((Double) step.get(1));
                this.episodeIsDone = ((Boolean) step.get(2));
                this.reward = new Domain<Double>(this.reward.doubleValue() + currReward);

                if (isTabular) {
                    // how to access older state from StateManager?
                    qLearning.update(lastState, (ArrayList<Domain>) this.stateMO.getI(),
                            idAction, new Domain<Double>(currReward));
                }
                else {
                    lfa.update(lastState, (ArrayList<Domain>) this.stateMO.getI(),
                            idAction, new Domain<Double>(currReward));
                }

            }
            else {
                this.currEpisode++;
                this.env.reset();
                this.episodeIsDone = false;
            }

            this.epsilon = Math.max(
                    this.epsilon - this.epsilonDecay,
                    this.epsilonFinal);
        }

        else {
            if (this.isTabular) {
                qLearning.serializeLearning(this.learningFileName);
            }
            else {
                lfa.serializeLearning(this.learningFileName);
            }
            // TODO -> finish training
        }
    }


    @Override
    public void accessMemoryObjects () {
        return;
    }
    @Override
    public void calculateActivation () {
        return;

    }
}

