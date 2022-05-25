package com.example.game.cst.sensor

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.FrogMindCommunicator

/*
    Gets the player's vision and stores it in the visionMO
*/

class Vision(var communicator: FrogMindCommunicator): Codelet() {
    var visionMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        visionMO = getOutput("VISION") as MemoryObject
    }

    override fun calculateActivation() {
        TODO("Not yet implemented")
    }

    override fun proc() {
        visionMO?.i = communicator.getVision()
    }
}