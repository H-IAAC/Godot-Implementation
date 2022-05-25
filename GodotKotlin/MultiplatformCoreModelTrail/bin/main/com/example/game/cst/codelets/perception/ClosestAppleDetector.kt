package com.example.game.cst.codelets.perception

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.environment.Apple
import godot.core.Vector2

class ClosestAppleDetector : Codelet() {
    /*
        Scans the list of known apples and updates the
        closestAppleMO accordingly. Stores null in the
        MO if there are no known apples
    */

    private var knownApplesMO: MemoryObject? = null
    private var closestAppleMO: MemoryObject? = null
    private var positionMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        knownApplesMO = getInput("KNOWN_APPLES") as MemoryObject
        positionMO = getInput("POSITION") as MemoryObject
        closestAppleMO = getOutput("CLOSEST_APPLE") as MemoryObject
    }

    override fun calculateActivation() {}

    override fun proc() {
        val knownApples: ArrayList<Apple> = knownApplesMO!!.i as ArrayList<Apple>
        if (knownApples.size > 0) {
            val position: Vector2 = positionMO!!.i as Vector2
            if (position != null) {
                var closestApple: Apple = knownApples[0]
                var minDistance: Double = position.distanceTo(closestApple.getPos())
                for (apple in knownApples) {
                    val newDistance: Double = position.distanceTo(apple.getPos())
                    if (newDistance < minDistance) {
                        closestApple = apple
                        minDistance = newDistance
                    }
                }
                closestAppleMO!!.i = closestApple
            }
        } else {
            closestAppleMO!!.i = null
        }
    }
}