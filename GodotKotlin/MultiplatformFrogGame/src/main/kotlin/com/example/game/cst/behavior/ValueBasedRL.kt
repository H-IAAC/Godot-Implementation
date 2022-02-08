package com.example.game.cst.behavior

import java.util.ArrayList
import java.util.Collections

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.Memory
import br.unicamp.cst.core.entities.MemoryObject

abstract class ValueBasedRL() {

/*
abstract class ValueBasedRL: Codelet protected constructor(
        var ALPHA: Double, var GAMMA: Double, var epsilon: Double,
        var epsilonDecay: Double, var finalEpsilon: Double,
        var numActions: Integer

) {
    // util
    protected var episodeReward: Double
    // parameters
    protected var numActions: Integer
    protected var alpha: Double
    protected var gamma: Double
    protected var epsilon: Double
    protected var epsilonDecay: Double
    protected var finalEpsilon: Double
    // memory objects
    protected var actionMO : MemoryObject? = null
    protected var lastStateMO : MemoryObject? = null
    protected var stateMO : MemoryObject? = null
    protected var rewardMO : MemoryObject? = null
    protected var infoMO : MemoryObject? = null
    protected var doneMO : MemoryObject? = null

    //constructor
    init {
        this.episodeReward = 0
        this.alpha = ALPHA
        this.gamma = GAMMA
        this.numActions = numActions
        this.epsilon = epsilon
        this.epsilonDecay = epsilonDecay
        this.finalEpsion = finalEpsilon
    }

    protected fun init(env: Environment?) {}
    protected fun update(state: ArrayList<Domain?>?, newState: ArrayList<Domain?>?,
                         action: Integer?, reward: Double?) {
    }

    protected operator fun getValue(state: ArrayList<Domain?>?, action: Integer?): Double {
        return -100.0
    }

    protected fun getValues(state: ArrayList<Domain?>?): ArrayList<Double> {
        return ArrayList<Double>()
    }

    protected fun getIdBestValue(state: ArrayList<Domain?>?): Integer {
        val vals: ArrayList<Double> = getValues(state)
        var maxV = -Double.MIN_VALUE
        var id: Integer = -1
        for (i in 0 until vals.size()) {
            if (vals.get(i) > maxV) {
                maxV = vals.get(i)
                id = i
            }
        }
        return id
    }

    protected fun getBestValue(state: ArrayList<Domain?>?): Double {
        return Collections.max(getValues(state))
    }

    protected fun epsilonGreedyPolicy(epsilon: Double, state: ArrayList<Domain?>?): Integer {
        return if (Math.random() < epsilon) Math.floor(Math.random() * numActions) else {
            getIdBestValue(state)
        }
    }

    protected fun updateEpsilon() {
        if (epsilon - epsilonDecay >= finalEpsilon)
            epsilon -= epsilonDecay
    }

    protected abstract fun proc()
    protected override fun accessMemoryObjects() {
        // WHY ARE DEFINING I/O OF MOs, INSTEAD OF CODELETS?
        stateMO = getInput("STATE") as MemoryObject
        lastStateMO = getInput("LAST_STATE") as MemoryObject
        rewardMO = getInput("REWARD") as MemoryObject
        doneMO = getInput("DONE") as MemoryObject
        infoMO = getInput("INFO") as MemoryObject
        actionMO = null // what about output?
    }

    // may be unnecessary
    protected abstract fun calculateActivation()

    */
}