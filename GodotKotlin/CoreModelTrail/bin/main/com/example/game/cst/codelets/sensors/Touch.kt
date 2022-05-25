package com.example.game.cst.codelets.sensors

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.CollectorMindCommunicator
import godot.global.GD


class Touch(var communicator: CollectorMindCommunicator) : Codelet() {
    /*
        Requests the apples in reach every proc. The body
        should respond to this request by updating the
        touchMO through the plugin's interface
    */

    private var touchMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        touchMO = getOutput("TOUCH") as MemoryObject?
    }

    override fun calculateActivation() {}

    override fun proc() {
        var apples = communicator.getApplesInReach()
        touchMO?.setI(apples)
    }
}
