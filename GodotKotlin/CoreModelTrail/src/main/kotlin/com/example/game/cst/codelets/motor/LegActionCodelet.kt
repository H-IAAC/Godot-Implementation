package com.example.game.cst.codelets.motor

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.CollectorMindCommunicator
import godot.core.Vector2

class LegActionCodelet(var communicator: CollectorMindCommunicator) : Codelet() {
    /*
        Sends commands from the legsMO to Godot as signals
    */

    private var legsMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        legsMO = getInput("LEGS") as MemoryObject
    }

    override fun calculateActivation() {}

    override fun proc() {
        val legAimPos: Vector2? = legsMO!!.i as Vector2?
        if (legAimPos == null) {
            communicator.forage()
        } else {
            communicator.goTo(legAimPos)
        }
    }
}