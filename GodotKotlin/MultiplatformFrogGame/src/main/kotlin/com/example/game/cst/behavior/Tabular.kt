mport java.util.ArrayList;
import java.util.Collections
import java.util.HashMap

class Tabular  //constructor
(alpha: Double, gamma: Double, epsilon: Double, epsilonDecay: Double, finalEpsilon: Double, numActions: Integer?) :
    ValueBasedRL(alpha, gamma, epsilon: Double, epsilonDecay: Double, finalEpsilon: Double, numActions: Integer?) {

    private val qTable: HashMap<ArrayList<Domain>, ArrayList<Double>>? = null

    protected fun initQValue(state: ArrayList<Domain?>?) {
        val initVals: ArrayList<Double> = ArrayList<Double>()
        for (i in 0 until numActions) initVals.add(Math.random())
        qTable.put(state, initVals)
    }

    @Override
    protected operator fun getValue(state: ArrayList<Domain?>?, idAction: Integer?): Double {
        val `val`: ArrayList<Double> = qTable.get(state)
        if (`val` == null) initQValue(state)
        return qTable.get(state).get(idAction)
    }

    @Override
    protected fun getValues(state: ArrayList<Domain?>?): ArrayList<Double> {
        return qTable.get(state)
    }

    @Override
    protected fun proc() {
        // HOW TO SET FIRST VALUES OF STATE AND LASTSTATE?
        var lastState : java.util.ArrayList<Domain>? // lastStateMO?.i as ArrayList<Domain>?
        // HOW TO COMMUNICATE IDACTION TO MOs?
        var idAction = super.epsilonGreedyPolicy(this.epsilon, state)
        // DOES IT WORK?
        lastState = state
        // variables declared in VBRL
        state = stateMO?.i as ArrayList<Domain>?
        reward = rewardMO?.i as Double
        done = doneMO?.i as Boolean
        info = infoMO?.i as String

        episodeReward += reward
        // (save this in array later to create graph)

        this.update(lastState, state, idAction, reward)
        if (done)
            episodeReward = 0
            super.updateEpsilon()
    }

    // @Override
    // protected fun accessMemoryObjects() {
    //     TODO("Not implemented yet")
    // }

    // @Override
    // protected fun calculateActivation() {
    //    TODO("Not implemented yet")
    // }

    @Override
    protected fun update(lastState: ArrayList<Domain?>?, state: ArrayList<Domain?>?, idAction: Integer?, reward: Double) {
        val maxFutureQ: Double = this.getBestValue(state)
        val qValues: ArrayList<Double> = getValues(lastState)
        var qVal: Double = qValues.get(idAction)
        qVal += this.ALPHA * (reward + this.GAMMA * maxFutureQ - qVal)
        qValues.set(idAction, qVal)
        // perhaps it is not even necessary! Passed my reference.
        qTable.put(lastState, qValues)
    }
}