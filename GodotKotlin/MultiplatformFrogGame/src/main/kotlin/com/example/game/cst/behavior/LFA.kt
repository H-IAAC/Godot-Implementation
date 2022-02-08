package com.example.game.cst.behavior

class LFA() {

/*
import java.util.ArrayList
import java.util.Collections
import java.util.LinkedHashMap
import java.util.Set
import com.example.game.cst.RLKotlin.FeaturesExtractor

class LFA(alpha: Double, gamma: Double, epsilon: Double, epsilonDecay: Double, finalEpsilon: Double,
          numActions: Integer?, fe: FeaturesExtractor) :
    ValueBasedRL(alpha, gamma, epsilon: Double, epsilonDecay: Double, finalEpsilon: Double, numActions) {

    private val weights: LinkedHashMap<String, Double> = LinkedHashMap<String, Double>()
    private val extractor // new FeaturesExtractor();
            : FeaturesExtractor

    class Extactor() {
        abstract fun getFeatures(state: ArrayList<Domain?>?, action: Integer?): LinkedHashMap<String, Double> {}
    }

    abstract fun getFeatures(state: ArrayList<Domain?>?, action: Integer?): LinkedHashMap<String, Double> {}

    init {
        extractor = fe
    }

    @Override
    fun init(env: Environment) {
        val gradient: LinkedHashMap<String, Double> = extractor.getFeatures(env.getObservationSpace(), 0)
        val features: Set<String> = gradient.keySet()
        for (f in features) {
            weights.put(f, Math.random())
        }
    }

    @Override
    fun update(lastState: ArrayList<Domain?>?, state: ArrayList<Domain?>?,
               action: Integer?, reward: Double) {
        val gradient: LinkedHashMap<String, Double> = extractor.getFeatures(lastState, action)
        val new_q_val: Double = this.getBestValue(state)
        val q_val = getValue(lastState, action)
        val target: Double = reward + this.GAMMA * new_q_val
        val name_features: Set<String> = gradient.keySet()

        for (f in name_features) {
            val w: Double = this.ALPHA * (target - q_val) * gradient.get(f)
            weights.put(f, w)
        }
    }

    fun getWeights(): LinkedHashMap<String, Double> {
        return weights
    }

    @Override
    protected operator fun getValue(state: ArrayList<Domain?>?, action: Integer?): Double {
        var q_val = 0.0
        val gradient: LinkedHashMap<String, Double> = extractor.getFeatures(state, action)
        val features: Set<String> = gradient.keySet()

        for (f in features) {
            q_val += weights.get(f) * gradient.get(f)
        }
        return q_val
    }

    @Override
    protected fun getValues(state: ArrayList<Domain?>?): ArrayList<Double>? {
        val vals: ArrayList<Double>? = null

        for (a in 0 until this.numActions) {
            vals.add(getValue(state, a))
        }
        return vals
    }

    @Override
    protected fun proc() {
        var lastState : ArrayList<Domain>?
        var idAction = super.epsilonGreedyPolicy(
            this.epsilon, state
        )
        lastState = state

        state = stateMO?.i as ArrayList<Domain>?
        reward = rewardMO?.i as Double
        info = infoMO?.i as String
        done = doneMO?.i as Boolean

        episodeReward += reward

        this.update(lastState, state, idAction, reward)
        if (done)
            episodeReward = 0
            super.updateEpsilon()
    }

//    @Override
//    protected fun accessMemoryObjects() {
//    }

//    @Override
//    protected fun calculateActivation() {
//    }

    */
}

