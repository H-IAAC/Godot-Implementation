package com.example.game.cst.codelets.behaviors

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.environment.Apple

class Forage : Codelet() {
    /*
        If there are no known apples, makes the legsMO
        null
    */

    private var knownApplesMO: MemoryObject? = null
    private var legsMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        knownApplesMO = getInput("KNOWN_APPLES") as MemoryObject
        legsMO = getOutput("LEGS") as MemoryObject
    }

    override fun calculateActivation() {}

    override fun proc() {
        val knownApples: ArrayList<Apple> = knownApplesMO!!.i as ArrayList<Apple>
        if (knownApples.size == 0) {
            legsMO?.i = null
        }
    }
}