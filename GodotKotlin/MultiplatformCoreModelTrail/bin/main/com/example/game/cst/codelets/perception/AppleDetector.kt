package com.example.game.cst.codelets.perception

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.environment.Apple

class AppleDetector : Codelet() {
    /*
        Scans the visionMO and includes all unknown apples
        in the knownApplesMO
    */

    private var visionMO: MemoryObject? = null
    private var knownApplesMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        synchronized(this) { visionMO = getInput("VISION") as MemoryObject }
        knownApplesMO = getOutput("KNOWN_APPLES") as MemoryObject
    }

    override fun calculateActivation() {}

    override fun proc() {
        var applesInSight: ArrayList<Apple> = visionMO?.i as ArrayList<Apple>
        var knownApples: ArrayList<Apple> = knownApplesMO?.i as ArrayList<Apple>
        for (apple in applesInSight) {
            if (apple !in knownApples) {
                knownApples.add(apple)
            }
        }
        knownApplesMO!!.i = knownApples
    }
}