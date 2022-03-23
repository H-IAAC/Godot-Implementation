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
import java.util.Hashtable;

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
    Long currEpisode = 0L;
    Integer currStep = 0;
    private CSV csvRewardRecord;
    // Serialization of q-table or weights. e.g. "q_learning.ser", "lfa_weights.ser"
    String learningFileName;
    // csv format. E.g. "cumulative_learning_lfa.csv"
    String cumRewardFileName;
    // for both learning and cumulative reward
    String localPathToCheckpoint;
    Boolean episodeIsDone = false;
    Domain reward = new Domain(0.0);
    Long checkpointEachNEpisodes;
    private Double greatestCheckpointReward = Double.MIN_VALUE;

    public LearnerCodelet(FrogMindCommunicator communicator,
                   Double alpha, Double gamma, Double epsilonInitial, Double epsilonFinal,
                   Long numEpisodes, Boolean isTraining, Boolean isTabular,
                   ValueBasedRL learning, Domain[] actionSpace,
                   String localPathToCheckpoint, String learningFileName,
                   String cumRewardFileName, Long checkpointEachNEpisodes) {
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
        this.localPathToCheckpoint = localPathToCheckpoint;
        this.learningFileName = learningFileName;
        this.cumRewardFileName = cumRewardFileName;
        this.checkpointEachNEpisodes = checkpointEachNEpisodes;
        setLearningType();
    }

    private void setLearningType() {
        if (this.isTabular) {
            this.qLearning =
                    new Tabular(this.alpha, this.gamma,
                            this.actionSpace[2].intValue(),
                            this.localPathToCheckpoint);
        } else {
            FeaturesExtractor fe = new FeaturesExtractor();
            this.lfa =
                    new LFA(this.alpha, this.gamma,
                            this.actionSpace[2].intValue(),
                            this.localPathToCheckpoint, fe);
        }
    }

    @Override
    public void proc() {
        if (this.currEpisode < this.numEpisodes) {
            
            if (this.currStep == 0 && this.currEpisode == 0) {
                if (Files.exists(
                        Path.of(this.localPathToCheckpoint + this.learningFileName))) {
                    this.deserializeLearning();
                }
            }

            if(!this.episodeIsDone) {
                Domain idAction;
                if (isTabular)
                    idAction =
                            qLearning.epsilonGreedyPolicy(this.epsilon, (
                                ArrayList<Domain>) this.stateMO.getI());
                else
                    idAction =
                            lfa.epsilonGreedyPolicy(this.epsilon, 
                                (ArrayList<Domain>) this.stateMO.getI());

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
                if ((this.currEpisode+1) % this.checkpointEachNEpisodes == 0) {
                    if (this.reward.doubleValue() > this.greatestCheckpointReward) {
                        this.greatestCheckpointReward = this.reward.doubleValue();
                        serializeLearning();
                    }
                }
                
                csvRewardRecord.recordNewEpisode(
                    this.currEpisode, this.reward, this.epsilon);
                this.reward = new Domain<Double>(0.0);
                this.currStep = 0;
                this.currEpisode++;
                this.env.reset();
                this.episodeIsDone = false;
            }

            this.epsilon = Math.max(
                    this.epsilon - this.epsilonDecay,
                    this.epsilonFinal);
        }

        else {
            serializeLearning(this.localPathToCheckpoint + "final_" + this.learningFileName);
            // TODO (is there more things to be done?) -> finish training
        }
    }

    private void serializeLearning() {
        if (this.isTabular) {
            qLearning.serializeLearning(this.localPathToCheckpoint + this.learningFileName);
        }
        else {
            lfa.serializeLearning(this.localPathToCheckpoint + this.learningFileName);
        }
    }

    private void serializeLearning(String path) {
        if (this.isTabular) {
            qLearning.serializeLearning(path);
        }
        else {
            lfa.serializeLearning(path);
        }
    }

    private void deserializeLearning() {
        if (this.isTabular) {
            qLearning.deserializeLearning(this.localPathToCheckpoint + this.learningFileName);
        }
        else {
            lfa.deserializeLearning(this.localPathToCheckpoint + this.learningFileName);
        }
    }

    @Override
    public void accessMemoryObjects () {
        stateMO = (MemoryObject) getInput("STATE");
    }
    @Override
    public void calculateActivation () {
        return;

    }
}

