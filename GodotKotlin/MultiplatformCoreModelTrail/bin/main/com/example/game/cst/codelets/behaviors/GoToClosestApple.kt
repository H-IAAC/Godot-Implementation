package com.example.game.cst.codelets.behaviors

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.environment.Apple

class GoToClosestApple : Codelet() {
    /*
        If it has a closest apple, sends a command to
        the legsMO with its position
    */

    private var closestAppleMO: MemoryObject? = null
    private var legsMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        closestAppleMO = getInput("CLOSEST_APPLE") as MemoryObject
        legsMO = getOutput("LEGS") as MemoryObject
    }

    override fun calculateActivation() {}

    override fun proc() {
        val closestApple: Apple? = closestAppleMO?.i as Apple?
        if (closestApple != null) {
            legsMO!!.i = closestApple.getPos()
        }
    }
}