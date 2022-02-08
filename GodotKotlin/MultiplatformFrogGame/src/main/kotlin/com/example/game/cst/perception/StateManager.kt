package com.example.game.cst.perception

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.cst.State
import com.example.game.godot.Car
import godot.core.Vector2

class StateManager: Codelet() {
    var positionMO: MemoryObject? = null
    var closestCarsMO: MemoryObject? = null
    var stateMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        positionMO = getInput("POSITION") as MemoryObject
        closestCarsMO = getInput("CLOSEST_CARS") as MemoryObject
        stateMO = getOutput("STATE") as MemoryObject
    }

    override fun calculateActivation() {
        TODO("Not yet implemented")
    }

    override fun proc() {
        stateMO?.i = State(positionMO?.i as Vector2, closestCarsMO?.i as ArrayList<Car>)
    }
}