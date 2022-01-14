package com.example.game.cst.perception

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.godot.Car
import godot.core.Vector2
import kotlin.math.abs

class CarCleaning: Codelet() {
    val MAX_DISTANCE = 7

    var knownCarsMO: MemoryObject? = null
    var positionMO: MemoryObject? = null

    private fun manhattanDistance(v1: Vector2, v2: Vector2): Double {
        return abs(v1[0] - v2[0]) + abs(v1[1] - v2[1])
    }

    override fun accessMemoryObjects() {
        knownCarsMO = getInput("KNOWN_CARS") as MemoryObject
        positionMO = getInput("POSITION") as MemoryObject
    }

    override fun calculateActivation() {
        TODO("Not yet implemented")
    }

    override fun proc() {
        var memoryList = knownCarsMO?.i as ArrayList<Car>
        var pos = positionMO?.i as Vector2

        for (i in 0 until memoryList.size) {
            if (manhattanDistance(memoryList[i].position, pos) > MAX_DISTANCE) {
                memoryList.removeAt(i)
            }
        }
    }
}