package com.example.game.cst.sensor

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.FrogMindCommunicator

class InnerSense(var communicator: FrogMindCommunicator): Codelet() {
    var positionMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        positionMO = getOutput("POSITION") as MemoryObject
    }

    override fun calculateActivation() {
        TODO("Not yet implemented")
    }

    override fun proc() {
        positionMO?.i = communicator.getPosition()
    }
}