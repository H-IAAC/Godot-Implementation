package com.example.game.cst.perception

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.cst.State
import com.example.game.cst.behavior.Domain
import com.example.game.godot.Car
import godot.core.Vector2

class StateManager: Codelet() {
    var positionMO: MemoryObject? = null
    var closestCarsMO: MemoryObject? = null
    var stateMO: MemoryObject? = null
    var lastStateMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        positionMO = getInput("POSITION") as MemoryObject
        closestCarsMO = getInput("CLOSEST_CARS") as MemoryObject
        stateMO = getOutput("STATE") as MemoryObject
        lastStateMO = getOutput("LAST_STATE") as MemoryObject
    }

    override fun calculateActivation() {
        TODO("Not yet implemented")
    }

    override fun proc() {
        var new_state = ArrayList<Domain<Double>>()
        var position = positionMO?.i as Vector2
        var closestCars = closestCarsMO?.i as ArrayList<Car>

        new_state.add(Domain<Double>(position.x))
        new_state.add(Domain<Double>(position.y))

        for (car: Car in closestCars) {
            new_state.add(Domain<Double>(car.cellPos.x))
            new_state.add(Domain<Double>(car.cellPos.y))
        }

        lastStateMO?.i = stateMO?.i
        stateMO?.i = new_state
    }
}