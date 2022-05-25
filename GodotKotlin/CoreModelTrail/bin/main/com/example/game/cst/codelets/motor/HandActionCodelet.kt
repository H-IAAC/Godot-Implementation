package com.example.game.cst.codelets.motor

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.environment.Apple
import com.example.game.CollectorMindCommunicator
import godot.global.GD

class HandActionCodelet(var communicator: CollectorMindCommunicator) : Codelet() {
    /*
        Sends commands from the handsMO to Godot as signals
    */

    private var handsMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        handsMO = getInput("HANDS") as MemoryObject
    }

    override fun calculateActivation() {}

    override fun proc() {
        val applesInHand: ArrayList<Apple> = handsMO!!.i as ArrayList<Apple>
        if (applesInHand.size > 0) {
            for (apple in applesInHand) {
                communicator.eatApple(apple)
            }
        }
    }
}
