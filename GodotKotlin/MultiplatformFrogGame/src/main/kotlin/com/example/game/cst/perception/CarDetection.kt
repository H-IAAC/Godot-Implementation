package com.example.game.cst.perception

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.godot.Car

/*
    Stores unknown cars in the visionMO in the long-term memory, defined by the knownCarsMO
*/

class CarDetection: Codelet() {
    var visionMO: MemoryObject? = null
    var knownCarsMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        visionMO = getInput("VISION") as MemoryObject
        knownCarsMO = getOutput("KNOWN_CARS") as MemoryObject
    }

    override fun calculateActivation() {
        TODO("Not yet implemented")
    }

    override fun proc() {
        var visionList = visionMO?.i as ArrayList<Car>
        var memoryList = knownCarsMO?.i as ArrayList<Car>

        for (i in 0 until visionList.size) {
            if (visionList[i] !in memoryList) {
                memoryList.add(visionList[i])
            }
        }
    }
}