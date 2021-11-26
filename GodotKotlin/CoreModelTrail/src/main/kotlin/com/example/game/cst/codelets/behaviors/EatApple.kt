package com.example.game.cst.codelets.behaviors

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.environment.Apple
import godot.global.GD

class EatApple : Codelet() {
    /*
        For all apples in reach, as per the touchMO,
        adds its name in the handsMO
    */

    private var touchMO: MemoryObject? = null
    private var handsMO: MemoryObject? = null

    override fun accessMemoryObjects() {
        touchMO = getInput("TOUCH") as MemoryObject
        handsMO = getOutput("HANDS") as MemoryObject
    }

    override fun calculateActivation() {}

    private fun getApplePositionByCode(list: ArrayList<Apple>, name: String): Int {
        for (i in list.indices) {
            if (list[i].getCode().equals(name)) {
                return i
            }
        }
        return -1
    }

    override fun proc() {
        val touchedApples: ArrayList<Apple> = touchMO!!.i as ArrayList<Apple>
        if (touchedApples.size > 0) {
            var applesInHand: ArrayList<Apple> = handsMO!!.i as ArrayList<Apple>
            for (apple in touchedApples) {
                if (apple !in applesInHand) {
                    applesInHand.add(apple)
                }
            }
            handsMO?.i = applesInHand
        } else {
            handsMO?.i = ArrayList<Apple>()
        }
    }
}